package ru.medals.ktor.medal.model.request

data class GetCountMedalsByCompanyRequest(
	val companyId: String? = null,
)