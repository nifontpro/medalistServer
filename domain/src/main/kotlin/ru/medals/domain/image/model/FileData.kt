package ru.medals.domain.image.model

data class FileData(
	val url: String = "",
	val filename: String = "",
	val description: String? = null,
	val size: Int = 0,
)