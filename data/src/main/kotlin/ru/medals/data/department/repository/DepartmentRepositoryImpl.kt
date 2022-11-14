package ru.medals.data.department.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.core.deleteImageFile
import ru.medals.data.core.errorBadImageKey
import ru.medals.data.core.errorS3
import ru.medals.data.department.model.DepartmentCol
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError
import ru.medals.domain.department.model.Department
import ru.medals.domain.department.repository.DepartmentRepository
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.image.repository.S3Repository

class DepartmentRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository
) : DepartmentRepository {

	private val departments = db.getCollection<DepartmentCol>()

	override suspend fun createDepartment(companyId: String): String? {
		val departmentCol = DepartmentCol(name = "", companyId = companyId)
		return if (departments.insertOne(departmentCol).wasAcknowledged()) {
			departmentCol.id
		} else {
			null
		}
	}

	override suspend fun getDepartmentById(id: String): Department? {
		return departments.findOneById(id)?.toDepartment()
	}

	/**
	 * Проверка, есть ли другой отдел с таким наименованием в компании
	 */
	override suspend fun doesOtherDepartmentWithName(
		name: String,
		companyId: String,
		departmentId: String
	): Boolean = departments.findOne(
		DepartmentCol::companyId eq companyId,
		DepartmentCol::id ne departmentId,
		DepartmentCol::name regex Regex("^$name\$", RegexOption.IGNORE_CASE)
	) != null

	override suspend fun getDepartmentsByCompany(companyId: String, filter: String?): List<Department> {
		return departments.find(
			DepartmentCol::companyId eq companyId,
			filter?.let { DepartmentCol::name regex Regex("$filter", RegexOption.IGNORE_CASE) }
		)
			.sort(ascending(DepartmentCol::name))
			.toList().map { it.toDepartment() }
	}

	override suspend fun updateDepartment(
		department: Department,
	): Boolean {
		return try {
			departments.updateOneById(
				id = department.id,
				update = set(
					DepartmentCol::name setTo department.name,
					DepartmentCol::description setTo department.description,
				)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun deleteDepartment(department: Department): Boolean {
		val isSuccess = departments.deleteOne(DepartmentCol::id eq department.id).wasAcknowledged()
		if (isSuccess && department.imageUrl != null) {
			deleteImageFile(department.imageUrl)
		}
		return isSuccess
	}

	override suspend fun getDepartmentsCount(companyId: String): Long {
		return departments.countDocuments(DepartmentCol::companyId eq companyId)
	}

	@Deprecated("Удалить в будущем")
	override suspend fun updateImage(
		departmentId: String,
		fileData: FileData,
	): Boolean {
		val department = departments.findOneById(departmentId) ?: return false
		val imageKey = "C${department.companyId}/departments/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return false

		val isSuccess = departments.updateOneById(
			id = departmentId,
			update = set(
				DepartmentCol::imageUrl setTo imageUrl,
				DepartmentCol::imageKey setTo imageKey
			),
		).wasAcknowledged()

		if (isSuccess) {
			// Удаляем старое изображение в s3
			department.imageKey?.let {
				s3repository.deleteObject(it)
			}
		} else {
			// Удаляем новое изображение в s3, если не обновились данные в БД
			s3repository.deleteObject(imageKey)
		}

		return isSuccess
	}

	override suspend fun addImage(departmentId: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val department = departments.findOneById(departmentId) ?: return errorDepartmentNotFound()
		val imageKey = "C${department.companyId}/departments/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		return try {
			departments.updateOneById(
				id = departmentId,
				push(
					DepartmentCol::images, ImageRef(
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

	override suspend fun updateImage(departmentId: String, imageKey: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val department = departments.findOneById(departmentId) ?: return errorDepartmentNotFound()
		val images = department.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey(REPO)

		s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		departments.updateOne(
			and(DepartmentCol::id eq departmentId, DepartmentCol::images / ImageRef::imageKey eq imageKey),
			setValue(DepartmentCol::images.posOp / ImageRef::description, fileData.description)
		)
		return RepositoryData.success()
	}

	override suspend fun deleteImage(departmentId: String, imageKey: String): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val department = departments.findOneById(departmentId) ?: return errorDepartmentNotFound()
		val images = department.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey(REPO)

		val isSuccess = departments.updateOneById(
			id = departmentId, pullByFilter(DepartmentCol::images, ImageRef::imageKey eq imageKey)
		).wasAcknowledged()

		return if (isSuccess) {
			s3repository.deleteObject(imageKey)
			RepositoryData.success()
		} else {
			errorBadUpdate()
		}
	}

	companion object {

		const val REPO = "department"

		private fun errorDepartmentNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "department not found",
				description = "Отдел не найден"
			)
		)

		private fun errorBadUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных отдела"
			)
		)
	}
}