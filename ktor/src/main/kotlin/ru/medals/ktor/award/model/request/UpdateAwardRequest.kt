package ru.medals.ktor.award.model.request

data class UpdateAwardRequest(
	val id: String? = null,
	val name: String? = null,
	val description: String? = null,
	val medalId: String? = null,
	val criteria: String? = null
)
