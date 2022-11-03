package ru.medals.ktor.medal.model.request

data class GetMedalByIdRequest(
	val medalId: String? = null,
)