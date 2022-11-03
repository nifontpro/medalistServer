package ru.medals.ktor.medal.model.request

data class UpdateMedalRequest(
	val id: String = "",
	val name: String = "",
	val description: String? = null,
	val score: Int = -1,
)