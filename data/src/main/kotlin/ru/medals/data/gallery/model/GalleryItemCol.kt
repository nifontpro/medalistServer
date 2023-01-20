package ru.medals.data.gallery.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.gallery.model.GalleryItem

data class GalleryItemCol(
	val name: String = "",
	val description: String? = null,
	val folderId: String = "",

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
		createDate = createDate,
		updateDate = updateDate,
		imageUrl = imageUrl,
		imageKey = imageKey,
		id = id
	)
}

@DslMarker
annotation class EntityDslBuild

class GalleryItemColBuild {
	var item = GalleryItem()
	var imgUrl = ""
	var imgKey = ""

	fun build() = GalleryItemCol(
		name = item.name,
		description = item.description,
		folderId = item.folderId,
		createDate = System.currentTimeMillis(),
		imageUrl = imgUrl,
		imageKey = imgKey,
		id = ObjectId().toString()
	)
}

@EntityDslBuild
fun galleryItemColBuild(block: GalleryItemColBuild.() -> Unit) = GalleryItemColBuild().apply(block).build()