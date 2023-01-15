package ru.medals.domain.gallery.model

data class Folder(
	val name: String = "",
	val description: String? = null,
	val parentId: String = "",
	val childrenIds: List<String> = emptyList(),

	val createDate: Long = -1,
	val updateDate: Long? = null,

	val imageUrl: String = "",
	val imageKey: String = "",

	val id: String = ""
)