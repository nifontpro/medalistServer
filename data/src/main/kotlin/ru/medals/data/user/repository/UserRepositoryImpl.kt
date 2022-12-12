package ru.medals.data.user.repository

import com.mongodb.client.model.UnwindOptions
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.core.*
import ru.medals.data.medal.model.MedalCol
import ru.medals.data.medal.repository.MedalRepoErrors.Companion.errorMedalNotFound
import ru.medals.data.user.model.*
import ru.medals.data.user.repository.UserDbProjection.Companion.projectUserFieldsWithDepNameAndAwards
import ru.medals.data.user.repository.UserDbProjection.Companion.projectUserFieldsWithDepartmentName
import ru.medals.data.user.repository.UserDbProjection.Companion.sortByAwardCountAndLastName
import ru.medals.data.user.repository.query.getUserByIdWithAwardsQuery
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.image.repository.S3Repository
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.model.UserAwardsLite
import ru.medals.domain.user.model.UserAwardsUnion
import ru.medals.domain.user.model.UserMedals
import ru.medals.domain.user.repository.UserRepository
import java.util.*

class UserRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository,
) : UserRepository {

	private val users = db.getCollection<UserCol>()
	private val medals = db.getCollection<MedalCol>()

	override suspend fun createUser(user: User): RepositoryData<User> {
		val userCol = user.toUserCol(isNew = true)
		return try {
			users.insertOne(userCol)
			RepositoryData.success(data = user.copy(id = userCol.id))
		} catch (e: Exception) {
			errorUserCreate()
		}
	}

	override suspend fun getUserById(id: String): User? {
		return users.findOneById(id)?.toUser()
	}

	override suspend fun getUserByIdWithDepartmentName(id: String): User? {
		return users.aggregate<UserCol>(
			match(UserCol::id eq id),
			lookup(from = "departmentCol", localField = "departmentId", foreignField = "_id", newAs = "department"),
			unwind("\$department", unwindOptions = UnwindOptions().preserveNullAndEmptyArrays(true)),
			projectUserFieldsWithDepartmentName
		).first()?.toUser()
	}

	/**
	 * Получить сотрудника со всеми его наградами
	 */
	override suspend fun getUserByIdWithAwards(userId: String): RepositoryData<UserAwardsUnion> {
		return try {
			val userAwards = users.aggregate<UserAwardsUnionCol>(
				getUserByIdWithAwardsQuery(userId = userId)
			).first()?.toUserAwardsUnion()
			RepositoryData.success(data = userAwards)
		} catch (e: Exception) {
			errorUserGet()
		}
	}

	override suspend fun getUsersByCompanyWithDepartmentName(companyId: String, filter: String?): List<User> {
		return users.aggregate<UserCol>(
			match(
				and(
					UserCol::companyId eq companyId,
					filter?.let {
						or(
							UserCol::name regex Regex("$filter", RegexOption.IGNORE_CASE),
							UserCol::lastname regex Regex("$filter", RegexOption.IGNORE_CASE),
						)
					}
				)
			),
			lookup(from = "departmentCol", localField = "departmentId", foreignField = "_id", newAs = "department"),
			unwind(fieldName = "\$department", unwindOptions = UnwindOptions().preserveNullAndEmptyArrays(true)),
			projectUserFieldsWithDepartmentName,
			sort(ascending(UserCol::lastname))
		).toList().map { it.toUser() }
	}

	/**
	 * Список сотрудников компании со списком их наград и именами отделов, в которых находятся
	 * Награды с самыми необходимыми полями (AwardLite)
	 */
	override suspend fun getUsersByCompanyWithAwards(
		companyId: String,
		filter: String?
	): RepositoryData<List<UserAwardsLite>> {
		return try {
			val userAwards = users.aggregate<UserAwardsLiteCol>(
				match(
					and(
						UserCol::companyId eq companyId,
						filter?.let {
							or(
								UserCol::name regex Regex("$filter", RegexOption.IGNORE_CASE),
								UserCol::lastname regex Regex("$filter", RegexOption.IGNORE_CASE),
							)
						}
					)
				),
				lookup(from = "departmentCol", localField = "departmentId", foreignField = "_id", newAs = "department"),
				unwind(fieldName = "\$department", unwindOptions = UnwindOptions().preserveNullAndEmptyArrays(true)),
				lookup(from = "awardCol", localField = "_id", foreignField = "relations.userId", newAs = "awards"),
				projectUserFieldsWithDepNameAndAwards,
				sortByAwardCountAndLastName,
			).toList().map { it.toUserAwards() }
			RepositoryData.success(data = userAwards)
		} catch (e: Exception) {
			errorUserGet()
		}
	}

	override suspend fun getUsersByCompany(companyId: String, filter: String?): List<User> {
		return users.find(
			UserCol::companyId eq companyId,
			filter?.let {
				or(
					UserCol::name regex Regex("$filter", RegexOption.IGNORE_CASE),
					UserCol::lastname regex Regex("$filter", RegexOption.IGNORE_CASE),
				)
			}
		)
			.ascendingSort(UserCol::lastname)
			.toList().map { it.toUser().copy(hashPassword = null) }
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

	override suspend fun verifyUserByIdExist(userId: String): Boolean {
		return users.countDocuments(UserCol::id eq userId) > 0
	}

	override suspend fun verifyUserByEmailExist(email: String): Boolean {
		return users.countDocuments(
			UserCol::email regex Regex("^$email\$", RegexOption.IGNORE_CASE),
		) > 0
	}

	override suspend fun getUserByLogin(login: String): User? {
		return users.findOne(
			UserCol::login eq login,
		)?.toUser()
	}

	override suspend fun verifyUserByLoginExist(login: String): Boolean {
		return users.countDocuments(
			UserCol::login eq login,
		) > 0
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
					UserCol::post setTo user.post,
					UserCol::phone setTo user.phone,
					UserCol::gender setTo user.gender,
					UserCol::description setTo user.description,
					UserCol::hashPassword setTo user.hashPassword
				)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun updateHashPassword(userId: String, hashPassword: String): Boolean {
		return try {
			users.updateOneById(
				id = userId,
				update = setValue(property = UserCol::hashPassword, value = hashPassword)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	/**
	 * Обновить количество наград у заданного пользователя
	 * @param [userId]
	 * @param [dCount] на сколько изменяется количество наград (приращение),
	 * может быть как положительным, так и отрицательным числом.
	 */
	override suspend fun updateAwardCount(userId: String, dCount: Int): RepositoryData<Unit> {
		return try {
			users.updateOneById(
				id = userId,
				update = inc(UserCol::awardCount, dCount)
			)
			RepositoryData.success()
		} catch (e: Exception) {
			errorUserAwardCountUpdate()
		}
	}

	override suspend fun delete(user: User): RepositoryData<User> {

		if (!s3repository.deleteAllImages(user)) return errorS3()

		return try {
			users.deleteOne(UserCol::id eq user.id)
			RepositoryData.success(data = user)
		} catch (e: Exception) {
			errorUserDelete()
		}
	}

	override suspend fun checkExist(userId: String): RepositoryData<Unit> {
		return try {
			if (users.countDocuments(UserCol::id eq userId) > 0) {
				RepositoryData.success()
			} else {
				errorUserNotFound()
			}
		} catch (e: Exception) {
			errorUserGet()
		}
	}

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

	/**
	 * Пересчитать количество наград у всех сотрудников
	 */
	override suspend fun calculateAwardCountOfUsers() {
		val allUsers = users.find().toList()
		allUsers.forEach { user ->
			val res = getUserByIdWithAwards(userId = user.id)
			val awardCount = if (res.success) res.data?.awardCount ?: 0 else 0
			users.updateOneById(
				id = user.id,
				update = set(UserCol::awardCount setTo awardCount)
			)
			println("User id: ${user.id}: ${user.lastname} ${user.name} ${user.patronymic} - $awardCount")
		}
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
				UserCol::imageUrl setTo imageUrl,
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
			errorUserUpdate()
		}
	}

	override suspend fun updateImage(userId: String, imageKey: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val user = users.findOneById(userId) ?: return errorUserNotFound()
		val images = user.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey("user")

		s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		users.updateOne(
			filter = and(UserCol::id eq userId, UserCol::images / ImageRef::imageKey eq imageKey),
			update = setValue(UserCol::images.posOp / ImageRef::description, fileData.description)
		)
		return RepositoryData.success()
	}

	override suspend fun deleteImage(userId: String, imageKey: String): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val user = users.findOneById(userId) ?: return errorUserNotFound()
		val images = user.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey("user")

		val isSuccess = users.updateOneById(
			id = userId, pullByFilter(UserCol::images, ImageRef::imageKey eq imageKey)
		).wasAcknowledged()

		return if (isSuccess) {
			s3repository.deleteObject(imageKey)
			RepositoryData.success()
		} else {
			errorUserUpdate()
		}
	}

	override suspend fun addMedal(userId: String, medalId: String): RepositoryData<Unit> {
		if (medals.countDocuments(MedalCol::id eq medalId) < 1) return errorMedalNotFound()

		users.updateOneById(
			id = userId,
			update = push(
				UserCol::medalsInfo, MedalInfoCol(medalId = medalId, createDate = Date())
			)
		)
		return RepositoryData.success()
	}

	override suspend fun getUserByIdWithMedals(userId: String): RepositoryData<UserMedals> {
		return try {
			val userMedals = users.aggregate<UserMedalsCol>(
				match(UserCol::id eq userId),
				lookup(from = "medalCol", localField = "medalsInfo.medalId", "_id", newAs = "medals"),
			).toList().firstOrNull()?.toUserMedal() ?: return errorUserNotFound()
			RepositoryData.success(data = userMedals)
		} catch (e: Exception) {
			errorUserGet()
		}
	}

	override suspend fun getUserByIdWithMedalsFilter(userId: String, medalId: String): RepositoryData<List<UserMedals>> {
		return try {
			val userMedals = users.aggregate<UserMedalsCol>(
				//			"[{\$match : {_id : {\$eq: '$userId'}}}]"
				"[{$Match: {'_id': '$userId', 'medalsInfo.medalId': '$medalId'}}," +
						"{$Project:{" +
						"_id: 1, email:1, login:1, name: 1, patronymic: 1, lastname: 1, role: 1, bio: 1, companyId: 1, departmentId: 1, score: 1, currentScore: 1, rewardCount: 1," +
						"medalsInfo: {\$filter: {input: '\$medalsInfo', as: 'infos'," +
						"cond: {$Eq: ['\$\$infos.medalId', '$medalId']}}}}" +
						"}," +
						"{$Lookup: {from: 'medalCol', localField: 'medalsInfo.medalId', foreignField: '_id', as: 'medals'}}" +
						"]"
			).toList().map { it.toUserMedal() }
			RepositoryData.success(data = userMedals)
		} catch (e: Exception) {
			errorUserGet()
		}
	}

}