package ru.medals.data.company.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.company.model.CompanyCol
import ru.medals.data.company.repository.CompanyRepoErrors.Companion.errorCompanyCreate
import ru.medals.data.company.repository.CompanyRepoErrors.Companion.errorCompanyDelete
import ru.medals.data.company.repository.CompanyRepoErrors.Companion.errorCompanyImageDelete
import ru.medals.data.company.repository.CompanyRepoErrors.Companion.errorCompanyImageNotFound
import ru.medals.data.company.repository.CompanyRepoErrors.Companion.errorCompanyNotFound
import ru.medals.data.company.repository.CompanyRepoErrors.Companion.errorCompanyUpdate
import ru.medals.data.core.errorBadImageKey
import ru.medals.data.core.errorS3
import ru.medals.domain.company.model.Company
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.core.bussines.model.RepositoryData
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

	override suspend fun createCompany(company: Company): RepositoryData<Company> {

		val companyCol = CompanyCol(
			name = company.name,
			description = company.description,
			phone = company.phone,
			email = company.email,
			address = company.address,
			ownerId = company.ownerId,
		)
		return try {
			companies.insertOne(companyCol)
			RepositoryData.success(data = companyCol.toCompany())
		} catch (e: Exception) {
			errorCompanyCreate()
		}
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
					CompanyCol::phone setTo company.phone,
					CompanyCol::email setTo company.email,
					CompanyCol::address setTo company.address,
				)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun deleteCompany(id: String): RepositoryData<Company> {

		val company = companies.findOneById(id) ?: return errorCompanyNotFound()
		if (!s3repository.deleteAllImages(company)) return errorS3()

		return try {
			companies.deleteOneById(id)
			RepositoryData.success(data = company.toCompany())
		} catch (e: Exception) {
			errorCompanyDelete()
		}
	}

	override suspend fun getCompanyById(id: String): Company? {
		return companies.findOneById(id)?.toCompany()
	}

	override suspend fun getCompanyByName(name: String): Company? {
		return companies.findOne(
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

	override suspend fun getCompanyCount(ownerId: String): Long {
		return companies.countDocuments(CompanyCol::ownerId eq ownerId)
	}

	/**
	 * Проверка, есть ли у владельца компания с таким наименованием
	 * (проверка при создании новой компании)
	 */
	override suspend fun doesCompanyByOwnerWithName(name: String, ownerId: String): Boolean {
		return companies.countDocuments(
			and(
				CompanyCol::ownerId eq ownerId,
				CompanyCol::name regex Regex("^$name\$", RegexOption.IGNORE_CASE)
			)
		) > 0
	}

	/**
	 * Проверка, есть ли другая компания у владельца с таким наименованием
	 * (проверка при обновлении данных)
	 */
	override suspend fun doesOtherCompanyByOwnerWithName(
		name: String,
		companyId: String,
		ownerId: String
	): Boolean = companies.countDocuments(
		and(
			CompanyCol::id ne companyId,
			CompanyCol::ownerId eq ownerId,
			CompanyCol::name regex Regex("^$name\$", RegexOption.IGNORE_CASE)
		)
	) > 0

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

	/**
	 * Удаление основного изображения
	 */

	override suspend fun deleteMainImage(companyId: String): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()

		try {
			val imageKey = companies.aggregate<CompanyCol>(
				match(CompanyCol::id eq companyId),
				project(CompanyCol::imageKey)
			).first()?.imageKey ?: return errorCompanyImageNotFound()

			if (!s3repository.deleteObject(key = imageKey)) return errorCompanyImageDelete()

			companies.updateOneById(
				id = companyId,
				update = set(
					CompanyCol::imageKey setTo null,
					CompanyCol::imageUrl setTo null,
				)
			)
			return RepositoryData.success()
		} catch (e: Exception) {
			return errorCompanyImageDelete()
		}
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
			errorCompanyUpdate()
		}
	}

	override suspend fun updateImage(companyId: String, imageKey: String, fileData: FileData): RepositoryData<Unit> {
		val company = companies.findOneById(companyId) ?: return errorS3()
		val images = company.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey("company")

		s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

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
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey("company")

		val isSuccess = companies.updateOneById(
			id = companyId, pullByFilter(CompanyCol::images, ImageRef::imageKey eq imageKey)
		).wasAcknowledged()

		return if (isSuccess) {
			s3repository.deleteObject(imageKey)
			RepositoryData.success()
		} else {
			errorCompanyUpdate()
		}
	}

}