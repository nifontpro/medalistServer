package ru.medals.data.company.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.company.model.CompanyCol
import ru.medals.data.core.deleteImageFile
import ru.medals.data.core.errorBadImageKey
import ru.medals.data.core.errorS3
import ru.medals.domain.company.model.Company
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.image.repository.S3Repository

class CompanyRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository
) : CompanyRepository {

	private val companies = db.getCollection<CompanyCol>()

	override suspend fun createEmptyCompany(ownerId: String): String? {

		val company = CompanyCol(
			name = "",
			description = "",
			ownerId = ownerId
		)
		return if (companies.insertOne(company).wasAcknowledged()) {
			company.id
		} else {
			null
		}
	}

	override suspend fun deleteCompany(id: String): Boolean {
		val company = companies.findOneById(id) ?: return false
		val isSuccess = companies.deleteOneById(id).wasAcknowledged()
		if (isSuccess && company.imageUrl != null) {
			deleteImageFile(company.imageUrl)
		}
		return isSuccess
	}

	override suspend fun getCompanyById(id: String): Company? {
		return companies.findOneById(id)?.toCompany()
	}

	override suspend fun getCompanyByName(name: String): Company? {
		return companies.findOne(
			CompanyCol::name regex Regex("^$name\$", RegexOption.IGNORE_CASE)
		)?.toCompany()
	}

	override suspend fun getOtherCompanyByName(name: String, companyId: String): Company? {
		return companies.findOne(
			CompanyCol::id ne companyId,
			CompanyCol::name regex Regex("^$name\$", RegexOption.IGNORE_CASE)
		)?.toCompany()
	}

	override suspend fun getAll(): List<Company> {
		return companies.find().toList().map {
			it.toCompany()
		}
	}

	override suspend fun getCompaniesByOwnerId(ownerId: String, filter: String?): List<Company> {
		return companies.find(
			CompanyCol::ownerId eq ownerId,
			filter?.let { CompanyCol::name regex Regex("$filter", RegexOption.IGNORE_CASE) }
		)
			.sort(ascending(CompanyCol::name))
			.toList().map { it.toCompany() }
	}

	override suspend fun updateCompanyProfile(
		company: Company
	): Boolean {
		return try {
			companies.updateOneById(
				id = company.id,
				update = set(
					CompanyCol::name setTo company.name,
					CompanyCol::description setTo company.description,
				)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun getCompanyCount(ownerId: String): Long {
		return companies.countDocuments(CompanyCol::ownerId eq ownerId)
	}

	/**
	 * Проверка, есть ли другая компания у владельца с таким наименованием
	 */
	override suspend fun doesOtherCompanyByOwnerWithName(
		name: String,
		companyId: String,
		ownerId: String
	): Boolean = companies.findOne(
		CompanyCol::id ne companyId,
		CompanyCol::ownerId eq ownerId,
		CompanyCol::name regex Regex("^$name\$", RegexOption.IGNORE_CASE)
	) != null

	@Deprecated("Удалить в будущем")
	override suspend fun updateImage(
		companyId: String,
		fileData: FileData,
	): Boolean {
		val company = companies.findOneById(companyId) ?: return false
		val imageKey = "C${companyId}/images/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return false

		val isSuccess = companies.updateOneById(
			id = companyId,
			update = set(
				CompanyCol::imageUrl setTo imageUrl,
				CompanyCol::imageKey setTo imageKey
			),
		).wasAcknowledged()

		if (isSuccess) {
			// Удаляем старое изображение в s3
			company.imageKey?.let {
				s3repository.deleteObject(it)
			}
		} else {
			// Удаляем новое изображение в s3, если не обновились данные в БД
			s3repository.deleteObject(imageKey)
		}

		return isSuccess
	}

	override suspend fun addImage(companyId: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		companies.findOneById(companyId) ?: return errorCompanyNotFound()

		val imageKey = "C${companyId}/images/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		return try {
			companies.updateOneById(
				id = companyId,
				push(
					CompanyCol::images, ImageRef(
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

	override suspend fun updateImage(companyId: String, imageKey: String, fileData: FileData): RepositoryData<Unit> {
		val company = companies.findOneById(companyId) ?: return errorS3()
		val images = company.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey(REPO)

		s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorBadImageKey(REPO)

		companies.updateOne(
			and(CompanyCol::id eq companyId, CompanyCol::images / ImageRef::imageKey eq imageKey),
			setValue(CompanyCol::images.posOp / ImageRef::description, fileData.description)
		)
		return RepositoryData.success()
	}


	override suspend fun deleteImage(companyId: String, imageKey: String): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val company = companies.findOneById(companyId) ?: return errorCompanyNotFound()
		val images = company.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey(REPO)

		val isSuccess = companies.updateOneById(
			id = companyId, pullByFilter(CompanyCol::images, ImageRef::imageKey eq imageKey)
		).wasAcknowledged()

		return if (isSuccess) {
			s3repository.deleteObject(imageKey)
			RepositoryData.success()
		} else {
			errorBadUpdate()
		}
	}

	companion object {

		const val REPO = "company"

		private fun errorCompanyNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "company not found",
				description = "Компания не найдена"
			)
		)

		private fun errorBadUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных организации"
			)
		)

	}
}