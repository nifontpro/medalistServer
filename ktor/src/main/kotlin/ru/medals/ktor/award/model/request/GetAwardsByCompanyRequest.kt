package ru.medals.ktor.award.model.request

data class GetAwardsByCompanyRequest(
	val companyId: String? = null,
	val filter: String? = null
)
