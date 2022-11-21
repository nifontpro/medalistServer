package ru.medals.data.medal.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.core.errorBadImageKey
import ru.medals.data.core.errorS3
import ru.medals.data.medal.model.MedalCol
import ru.medals.data.medal.repository.MedalRepoErrors.Companion.errorMedalUpdate
import ru.medals.data.medal.repository.MedalRepoErrors.Companion.errorMedalDelete
import ru.medals.data.medal.repository.MedalRepoErrors.Companion.errorMedalNotFound
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.image.repository.S3Repository
import ru.medals.domain.medal.model.Medal
import ru.medals.domain.medal.repository.MedalRepository

class MedalRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository
) : MedalRepository {

	private val medals = db.getCollection<MedalCol>()

	override suspend fun createEmptyMedal(isSystem: Boolean, companyId: String?): String? {
		val medal = MedalCol(companyId = companyId, isSystem = isSystem)
		return if (medals.insertOne(medal).wasAcknowledged()) {
			medal.id
		} else {
			null
		}
	}

	override suspend fun getMedalById(id: String): Medal? {
		return medals.findOneById(id)?.toMedal()
	}

	override suspend fun getCompanyMedals(companyId: String, filter: String?): List<Medal> {
		return medals.find(
			MedalCol::companyId eq companyId,
			filter?.let { MedalCol::name regex Regex("$filter", RegexOption.IGNORE_CASE) }
		).toList().map { it.toMedal() }
	}

	override suspend fun updateMedal(medal: Medal): Boolean {
		return try {
			medals.updateOneById(
				id = medal.id,
				update = set(
					MedalCol::name setTo medal.name,
					MedalCol::description setTo medal.description,
					MedalCol::score setTo medal.score,
				)
			).wasAcknowledged()
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun deleteMedal(medal: Medal): RepositoryData<Medal> {

		val medalCol = medals.findOneById(medal.id) ?: return errorMedalNotFound()
		if (!s3repository.deleteAllImages(medalCol)) return errorS3()

		return try {
			medals.deleteOneById(medal.id)
			RepositoryData.success(data = medalCol.toMedal())
		} catch (e: Exception) {
			errorMedalDelete()
		}
	}

	override suspend fun getCountByCompany(companyId: String): Long {
		return medals.countDocuments(MedalCol::companyId eq companyId)
	}

	@Deprecated("Удалить в будущем")
	override suspend fun updateImage(
		medalId: String,
		fileData: FileData,
	): Boolean {
		try {
			val medal = medals.findOneById(medalId) ?: return false
			val imageKey = "C${medal.companyId}/medals/${fileData.filename}"
			val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return false

			val isSuccess = medals.updateOneById(
				id = medalId,
				update = set(
					MedalCol::imageUrl setTo imageUrl,
					MedalCol::imageKey setTo imageKey
				),
			).wasAcknowledged()

			if (isSuccess) {
				// Удаляем старое изображение в s3
				medal.imageKey?.let {
					s3repository.deleteObject(key = it)
				}
			} else {
				// Удаляем новое изображение в s3, если не обновились данные в БД
				s3repository.deleteObject(key = imageKey)
			}

			return isSuccess
		} catch (e: Exception) {
			println(e.stackTrace)
			return false
		}
	}

	override suspend fun addImage(medalId: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val medal = medals.findOneById(medalId) ?: return errorMedalNotFound()
		val imageKey = "C${medal.companyId}/medals/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		return try {
			medals.updateOneById(
				id = medalId,
				push(
					MedalCol::images, ImageRef(
						imageKey = imageKey,
						imageUrl = imageUrl,
						description = fileData.description
					)
				)
			)
			RepositoryData.success()
		} catch (e: Exception) {
			s3repository.deleteObject(imageKey)
			errorMedalUpdate()
		}
	}

	override suspend fun updateImage(medalId: String, imageKey: String, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val medal = medals.findOneById(medalId) ?: return errorMedalNotFound()
		val images = medal.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey("medals")

		s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		medals.updateOne(
			and(MedalCol::id eq medalId, MedalCol::images / ImageRef::imageKey eq imageKey),
			setValue(MedalCol::images.posOp / ImageRef::description, fileData.description)
		)
		return RepositoryData.success()
	}

	override suspend fun deleteImage(medalId: String, imageKey: String): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val medal = medals.findOneById(medalId) ?: return errorMedalNotFound()
		val images = medal.images
		if (images.find { imageRef -> imageRef.imageKey == imageKey } == null) return errorBadImageKey("medals")

		val isSuccess = medals.updateOneById(
			id = medalId, pullByFilter(MedalCol::images, ImageRef::imageKey eq imageKey)
		).wasAcknowledged()

		return if (isSuccess) {
			s3repository.deleteObject(imageKey)
			RepositoryData.success()
		} else {
			errorMedalUpdate()
		}
	}

}