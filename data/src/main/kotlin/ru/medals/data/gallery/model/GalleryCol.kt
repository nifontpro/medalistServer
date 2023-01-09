package ru.medals.data.gallery.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.gallery.model.Gallery

data class GalleryCol(
	val name: String = "",
	val description: String? = null,
	val folderId: String? = null,
	val countLink: Int = -1, // Число ссылок на объект

	val createDate: Long = 0,
	val updateDate: Long? = null,

	val imageUrl: String = "",
	val imageKey: String = "",

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toGallery() = Gallery(
		name = name,
		description = description,
		folderId = folderId,
		countLink = countLink,
		createDate = createDate,
		updateDate = updateDate,
		imageUrl = imageUrl,
		imageKey = imageKey,
		id = id
	)
}