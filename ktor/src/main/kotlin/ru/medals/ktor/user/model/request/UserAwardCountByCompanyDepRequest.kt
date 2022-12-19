package ru.medals.ktor.user.model.request

data class UserAwardCountByCompanyDepRequest(
	val companyId: String? = null,
)