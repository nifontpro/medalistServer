package ru.medals.ktor.medal.model.request

data class CreateMedalRequest(
	val companyId: String? = null,
	val isSystem: Boolean = false
)