package ru.medals.ktor.award.model.request

data class GetAwardCountByCompanyRequest(
	val companyId: String? = null,
)
