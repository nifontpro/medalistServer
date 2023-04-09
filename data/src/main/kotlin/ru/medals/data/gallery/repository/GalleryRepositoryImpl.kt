package ru.medals.data.gallery.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.core.errorS3
import ru.medals.data.core.model.sortStep
import ru.medals.data.gallery.model.GalleryItemCol
import ru.medals.data.gallery.model.galleryItemColBuild
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGalleryCreate
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGalleryDelete
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGalleryNotFound
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGalleryUpdate
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGetGallery
import ru.medals.domain.core.bussines.model.BaseQueryValid
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.domain.gallery.repository.GalleryRepository
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.repository.S3Repository

class GalleryRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository
) : GalleryRepository {
	private val gallery = db.getCollection<GalleryItemCol>()

	/**
	 * Создание объекта галереи
	 */
	override suspend fun addItem(galleryItem: GalleryItem, fileData: FileData): RepositoryData<GalleryItem> {
		if (!s3repository.available()) return errorS3()
		val imageKey = "F-${galleryItem.folderId}/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()

		return try {
			val galleryItemCol = galleryItemColBuild {
				item = galleryItem
				imgUrl = imageUrl
				imgKey = imageKey
			}

			gallery.insertOne(galleryItemCol)
			RepositoryData.success(data = galleryItemCol.toGalleryItem())
		} catch (e: Exception) {
			s3repository.deleteObject(key = imageKey, system = true)
			errorGalleryCreate()
		}
	}

	/**
	 * Удаление объекта галереи
	 */
	override suspend fun delete(item: GalleryItem): RepositoryData<GalleryItem> {
		if (!s3repository.deleteObject(key = item.imageKey, system = true)) return errorS3()
		return try {
			gallery.deleteOneById(id = item.id)
			RepositoryData.success(data = item)
		} catch (e: Exception) {
			errorGalleryDelete()
		}
	}

	override suspend fun getById(id: String): RepositoryData<GalleryItem> {
		return try {
			val item = gallery.findOneById(id = id) ?: return errorGalleryNotFound()
			RepositoryData.success(data = item.toGalleryItem())
		} catch (e: Exception) {
			errorGetGallery()
		}
	}

	/**
	 * Получить список объектов из папки
	 */
	override suspend fun getByFolder(
		folderId: String,
		baseQuery: BaseQueryValid
	): RepositoryData<List<GalleryItem>> {
		return try {

			val filter = and(
				GalleryItemCol::folderId eq folderId,
				baseQuery.filter?.let {
					GalleryItemCol::name regex Regex(it, RegexOption.IGNORE_CASE)
				}
			)

			val query = mutableListOf(match(filter))
			baseQuery.field?.let { query += sortStep(baseQuery) }
			query += skip(baseQuery.page * baseQuery.pageSize)
			query += limit(baseQuery.pageSize)

			val items = gallery.aggregate<GalleryItemCol>(query).toList().map { it.toGalleryItem() }
			RepositoryData.success(data = items)
		} catch (e: Exception) {
			errorGetGallery()
		}
	}

	override suspend fun updateImage(item: GalleryItem, fileData: FileData): RepositoryData<Unit> {
		s3repository.putObject(key = item.imageKey, fileData = fileData) ?: return errorS3()
		return RepositoryData.success()
	}

	override suspend fun update(item: GalleryItem): RepositoryData<GalleryItem> {
		return try {
			val now = System.currentTimeMillis()
			gallery.updateOneById(
				id = item.id,
				update = set(
					GalleryItemCol::name setTo item.name,
					GalleryItemCol::description setTo item.description,
					GalleryItemCol::folderId setTo item.folderId,
					GalleryItemCol::updateDate setTo now
				)
			)
			RepositoryData.success(data = item.copy(updateDate = now))
		} catch (e: Exception) {
			errorGalleryUpdate()
		}
	}
}