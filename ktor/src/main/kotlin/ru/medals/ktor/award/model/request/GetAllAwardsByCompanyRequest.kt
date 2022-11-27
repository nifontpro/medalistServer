package ru.medals.ktor.award.model.request

data class GetAllAwardsByCompanyRequest(
	val companyId: String? = null,
	val filter: String? = null
)
