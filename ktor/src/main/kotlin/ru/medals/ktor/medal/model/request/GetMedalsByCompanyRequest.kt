package ru.medals.ktor.medal.model.request

data class GetMedalsByCompanyRequest(
	val companyId: String? = null,
	val filter: String? = null,
)