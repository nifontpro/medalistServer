package ru.medals.domain.gallery.model

data class GalleryItem(
	val name: String = "",
	val description: String? = null,
	val folderId: String = "",

	val createDate: Long = 0,
	val updateDate: Long? = null,

	val imageUrl: String = "",
	val imageKey: String = "",

	val id: String = ""
)