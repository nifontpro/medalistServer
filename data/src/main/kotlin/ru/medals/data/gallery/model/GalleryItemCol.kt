package ru.medals.data.gallery.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.gallery.model.GalleryItem

data class GalleryItemCol(
	val name: String = "",
	val description: String? = null,
	val folderId: String = "",
	val countLink: Int = -1, // Число ссылок на объект

	val createDate: Long = -1,
	val updateDate: Long? = null,

	val imageUrl: String = "",
	val imageKey: String = "",

	@BsonId
	val id: String = ""
) {
	fun toGalleryItem() = GalleryItem(
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

@DslMarker
annotation class GalleryDslBuild

class GalleryItemColBuild {
	var item = GalleryItem()
	var imgUrl = ""
	var imgKey = ""

	fun build() = GalleryItemCol(
		name = item.name,
		description = item.description,
		folderId = item.folderId,
		countLink = 0,
		createDate = System.currentTimeMillis(),
		imageUrl = imgUrl,
		imageKey = imgKey,
		id = ObjectId().toString()
	)
}

@GalleryDslBuild
fun galleryItemColBuild(block: GalleryItemColBuild.() -> Unit) = GalleryItemColBuild().apply(block).build()