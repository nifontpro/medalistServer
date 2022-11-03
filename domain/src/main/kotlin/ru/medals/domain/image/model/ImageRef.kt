package ru.medals.domain.image.model

data class ImageRef(
	val id: String = "delete field", // Del
	val imageUrl: String = "",
	val imageKey: String = "",
	val description: String? = null
)
