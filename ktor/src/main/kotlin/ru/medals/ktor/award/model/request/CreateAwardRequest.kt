package ru.medals.ktor.award.model.request

data class CreateAwardRequest(
	val name: String? = null,
	val description: String? = null,
	val companyId: String? = null,
	val medalId: String? = null,
	val criteria: String? = null
)
