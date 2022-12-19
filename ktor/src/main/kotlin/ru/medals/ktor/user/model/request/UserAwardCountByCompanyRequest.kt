package ru.medals.ktor.user.model.request

data class UserAwardCountByCompanyRequest(
	val companyId: String? = null,
)