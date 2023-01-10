package ru.medals.data.gallery.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.core.errorS3
import ru.medals.data.gallery.model.GalleryItemCol
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

	override suspend fun addItem(galleryItem: GalleryItem, fileData: FileData): RepositoryData<Unit> {
		if (!s3repository.available()) return errorS3()
		val imageKey = "F${galleryItem.folderId}/${fileData.filename}"
		val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return errorS3()
		separator()
		println(imageUrl)

		return RepositoryData.success()
	}

}