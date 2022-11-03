package ru.medals.ktor.user.model.request

data class GetBestUsersByCompanyRequest(
	val companyId: String? = null,
	val limit: Int? = null
)