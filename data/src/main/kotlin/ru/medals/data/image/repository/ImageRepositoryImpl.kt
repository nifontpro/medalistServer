package ru.medals.data.image.repository

import org.bson.types.Binary
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.image.model.ImageCol
import ru.medals.data.image.model.TimeCol
import ru.medals.domain.image.repository.ImageRepository
import java.util.*

class ImageRepositoryImpl(
	db: CoroutineDatabase
) : ImageRepository {

	private val images = db.getCollection<ImageCol>()
	private val times = db.getCollection<TimeCol>()

	override suspend fun create(fileData: ByteArray, fileName: String?): String? {
		val imageCol = ImageCol(
			image = Binary(fileData),
			fileName = fileName,
			createDate = Date()
		)

		return if (images.insertOne(imageCol).wasAcknowledged()) {
			imageCol.id
		} else {
			null
		}
	}

	override suspend fun delete(imageId: String) = images.deleteOneById(imageId).wasAcknowledged()

	override suspend fun getDataById(imageId: String): ByteArray? {
		return images.findOneById(imageId)?.image?.data
	}

	override suspend fun update(imageId: String, fileData: ByteArray, fileName: String?): Boolean {
		return images.updateOneById(
			id = imageId,
			ImageCol(
				id = imageId,
				image = Binary(fileData),
				fileName = fileName,
				updateDate = Date()
			)
		).wasAcknowledged()
	}

	override suspend fun createTime(timestamp: Long): String? {
		val time = TimeCol(timestamp = Date(timestamp))
		return if (times.insertOne(time).wasAcknowledged()) {
			time.id
		} else {
			null
		}
	}

	override suspend fun getTimeById(id: String): Long? {
		return times.findOneById(id)?.timestamp?.time
	}

}
