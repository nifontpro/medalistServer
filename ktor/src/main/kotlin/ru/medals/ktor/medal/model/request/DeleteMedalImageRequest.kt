package ru.medals.ktor.medal.model.request

data class DeleteMedalImageRequest(
	val medalId: String? = null,
	val imageKey: String? = null,
)