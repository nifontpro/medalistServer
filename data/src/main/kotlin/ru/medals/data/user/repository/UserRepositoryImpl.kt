package ru.medals.data.user.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.core.deleteImageFile
import ru.medals.data.core.errorBadImageKey
import ru.medals.data.core.errorS3
import ru.medals.data.user.model.UserCol
import ru.medals.data.user.model.toUserCol
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.image.repository.S3Repository
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.repository.UserRepository

class UserRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository
) : UserRepository {

	private val users = db.getCollection<UserCol>()

	override suspend fun createUser(user: User): String? {
		val userCol = user.toUserCol(isNew = true)
		return if (users.insertOne(userCol).wasAcknowledged()
		) {
			userCol.id
		} else {
			null
		}
	}

	override suspend fun getUserById(id: String): User? {
		return users.findOneById(id)?.toUser()
	}

	/*    override suspend fun getUserByCredential(login: String): User? {
					return users.findOne(
							or(
									User::email regex Regex("^$login\$", RegexOption.IGNORE_CASE),
									User::login eq login
							)
					)
			}*/

	override suspend fun getUserByEmail(email: String): User? {
		return users.findOne(
			UserCol::email regex Regex("^$email\$", RegexOption.IGNORE_CASE),
		)?.toUser(clearHashPassword = false)
	}

	override suspend fun getUserByLogin(login: String): User? {
		return users.findOne(
			UserCol::login eq login,
		)?.toUser()
	}

	override suspend fun getCompanyAdmins(companyId: String, filter: String?): List<User> {
		return users.find(
			UserCol::companyId eq companyId,
			UserCol::role eq ADMIN,
			filter?.let {
				or(
					UserCol::name regex Regex("$filter", RegexOption.IGNORE_CASE),
					UserCol::patronymic regex Regex("$filter", RegexOption.IGNORE_CASE),
					UserCol::lastname regex Regex("$filter", RegexOption.IGNORE_CASE),
				)
			}
		)
			.sort(ascending(UserCol::lastname))
			.toList().map { it.toUser() }
	}

	override suspend fun getDirectorDepartment(departmentId: String): User? {
		return users.findOne(
			UserCol::departmentId eq departmentId,
			UserCol::role eq DIRECTOR
		)?.toUser()
	}

	override suspend fun getUsersByDepartment(departmentId: String, filter: String?): List<User> {
		return users.find(
			UserCol::departmentId eq departmentId,
			filter?.let {
				or(
					UserCol::name regex Regex("$filter", RegexOption.IGNORE_CASE),
					UserCol::patronymic regex Regex("$filter", RegexOption.IGNORE_CASE),
					UserCol::lastname regex Regex("$filter", RegexOption.IGNORE_CASE),
				)
			}
		)
			.ascendingSort(UserCol::lastname)
			.toList().map { it.toUser().copy(hashPassword = null) }
	}

	override suspend fun updateProfile(user: User): Boolean {
		return try {
			users.updateOneById(
				id = user.id,
				update = set(
					UserCol::email setTo user.email,
					UserCol::login setTo user.login,
					UserCol::name setTo user.name,
					UserCol::patronymic setTo user.patronymic,
					UserCol::lastname setTo user.lastname,
					UserCol::bio setTo user.bio,
				)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun delete(user: User): Boolean {
		val isSuccess = users.deleteOne(UserCol::id eq user.id).wasAcknowledged()
		if (isSuccess && user.imageUrl != null) {
			deleteImageFile(user.imageUrl)
		}
		return isSuccess
	}

	/*	override suspend fun getUsersContainsMedal(medalId: String): List<UserResponse> {
			return users.aggregate<User>(match(User::medals contains medalId)).toList().map {
				it.toUserResponse()
			}
		}*/

	override suspend fun getUsersCountByCompany(companyId: String): Long {
		return users.countDocuments(UserCol::companyId eq companyId)
	}

	override suspend fun getUsersCountByDepartment(departmentId: String): Long {
		return users.countDocuments(UserCol::departmentId eq departmentId)
	}

	override suspend fun getBestUsersByCompany(companyId: String, limit: Int): List<User> {
		return users.find(UserCol::companyId eq companyId)
			.sort(descending(UserCol::score))
			.limit(limit)
			.toList().map { it.toUser() }
	}

	/**
	 * Получить всех членов номинационной комиссии
	 */
	override suspend fun getAllMnc(companyId: String): List<User> {
		return users.find(UserCol::companyId eq companyId, UserCol::mnc eq true)
			.sort(ascending(UserCol::name))
			.toList().map {
				it.toUser()
			}
	}

	override suspend fun getAllMncIds(companyId: String): List<String> {
		return getAllMnc(companyId).map { it.id }
	}

	@Deprecated("Удалить в будущем")
	override suspend fun updateImage(
		userId: String,
		fileData: FileData,
	): Boolean {
		val user = users.findOneById(userId) ?: return false
		val prefix = if (user.companyId.isNullOrBlank()) "O" else "C${user.companyId}/users"
		val imageKey = "$prefix/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return false

		val isSuccess = users.updateOneById(
			id = userId,
			update = set(
				UserCol::profileImageUrl setTo imageUrl,
				UserCol::imageKey setTo imageKey
			),
		).wasAcknowledged()

		if (isSuccess) {
			// Удаляем старое изображение в s3
			user.imageKey?.let {
				s3repository.deleteObject(it)
			}
		} else {
			// Удаляем новое изображение в s3, если не обновились данные в БД
			s3repository.deleteObject(imageKey)
		}

		return isSuccess
	}

	override suspend fun addImage(userId: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val user = users.findOneById(userId) ?: return errorUserNotFound()

		val prefix = if (user.companyId.isNullOrBlank()) "O" else "C${user.companyId}/users"
		val imageKey = "$prefix/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		return try {
			users.updateOneById(
				id = userId,
				push(
					UserCol::images, ImageRef(
						imageKey = imageKey,
						imageUrl = imageUrl,
						description = fileData.description
					)
				)
			)
			RepositoryData.success()
		} catch (e: Exception) {
			s3repository.deleteObject(imageKey)
			errorBadUpdate()
		}
	}

	override suspend fun updateImage(userId: String, imageKey: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val user = users.findOneById(userId) ?: return errorUserNotFound()
		val images = user.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey(REPO)

		s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		users.updateOne(
			and(UserCol::id eq userId, UserCol::images / ImageRef::imageKey eq imageKey),
			setValue(UserCol::images.posOp / ImageRef::description, fileData.description)
		)
		return RepositoryData.success()
	}

	override suspend fun deleteImage(userId: String, imageKey: String): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val user = users.findOneById(userId) ?: return errorUserNotFound()
		val images = user.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey(REPO)

		val isSuccess = users.updateOneById(
			id = userId, pullByFilter(UserCol::images, ImageRef::imageKey eq imageKey)
		).wasAcknowledged()

		return if (isSuccess) {
			s3repository.deleteObject(imageKey)
			RepositoryData.success()
		} else {
			errorBadUpdate()
		}
	}

	companion object {

		const val REPO = "user"

		private fun errorUserNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "user not found",
				description = "Сотрудник не найден"
			)
		)

		private fun errorBadUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных сотрудника"
			)
		)

	}
}