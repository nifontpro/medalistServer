package ru.medals.ktor.user.model.request

data class GetUsersByCompanyRequest(
	val companyId: String? = null,
	val filter: String? = null
)