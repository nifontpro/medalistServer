package ru.medals.ktor.user.model.request

data class UserCountByCompanyRequest(
	val companyId: String? = null,
)