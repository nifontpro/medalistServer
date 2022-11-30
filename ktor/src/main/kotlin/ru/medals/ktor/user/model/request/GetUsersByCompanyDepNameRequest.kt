package ru.medals.ktor.user.model.request

data class GetUsersByCompanyDepNameRequest(
	val companyId: String? = null,
	val filter: String? = null
)