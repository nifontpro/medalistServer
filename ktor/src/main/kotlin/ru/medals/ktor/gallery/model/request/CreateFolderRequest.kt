package ru.medals.ktor.gallery.model.request

data class CreateFolderRequest(
	val parentId: String = "",
	val name: String = ""
)

