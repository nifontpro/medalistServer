package ru.medals.ktor.award.model.request

data class SetAwardGalleryImageRequest(
	val awardId: String = "",
	val galleryItemId: String = ""
)
