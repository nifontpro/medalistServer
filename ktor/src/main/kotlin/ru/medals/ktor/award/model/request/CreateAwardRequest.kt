package ru.medals.ktor.award.model.request

data class CreateAwardRequest(
	val name: String? = null,
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Long? = null,
	val endDate: Long? = null,
	val score: Int? = null,
	val companyId: String? = null,
)
