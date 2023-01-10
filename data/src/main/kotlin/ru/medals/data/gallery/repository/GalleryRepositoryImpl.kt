package ru.medals.data.gallery.repository

import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.conversions.Bson
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.core.errorS3
import ru.medals.data.core.limitStep
import ru.medals.data.core.skipStep
import ru.medals.data.gallery.model.GalleryItemCol
import ru.medals.data.gallery.model.galleryItemColBuild
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGalleryCreate
import ru.medals.data.gallery.repository.GalleryRepoErrors.Companion.errorGetGallery
import ru.medals.domain.core.bussines.model.BaseQuery
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.util.separator
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

	private fun sortStep(baseQuery: BaseQuery): Bson {
		val direct = BsonInt32(baseQuery.direction ?: 1)
		return when (baseQuery.field) {
			GalleryItemCol::name.path() -> sort(
				BsonDocument().append(GalleryItemCol::name.path(), direct)
			)

			null -> skip(0)
			else -> {
				sort(
					BsonDocument()
						.append(baseQuery.field, direct)
						.append(GalleryItemCol::name.path(), BsonInt32(1))
				)
			}
		}
	}

	override suspend fun getByFolder(folderId: String, baseQuery: BaseQuery): RepositoryData<List<GalleryItem>> {
		return try {
			val filter = and(
				GalleryItemCol::folderId eq folderId,
				baseQuery.filter?.let {
					GalleryItemCol::name regex Regex(it, RegexOption.IGNORE_CASE)
				}
			)
			val count = gallery.countDocuments(
				filter = filter,
			)

			separator()
			println("count = $count")

			val items = gallery.aggregate<GalleryItemCol>(
				match(filter),
				sortStep(baseQuery),
				skipStep(baseQuery),
				limitStep(baseQuery)
			).toList().map { it.toGalleryItem() }
			RepositoryData.success(data = items)
		} catch (e: Exception) {
			errorGetGallery()
		}
	}
}