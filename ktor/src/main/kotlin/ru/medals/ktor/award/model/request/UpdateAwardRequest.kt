package ru.medals.ktor.award.model.request

data class UpdateAwardRequest(
	val id: String? = null,
	val name: String? = null,
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Long? = null,
	val endDate: Long? = null,
	val score: Int? = null,
)
