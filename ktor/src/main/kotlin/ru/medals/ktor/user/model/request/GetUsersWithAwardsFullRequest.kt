package ru.medals.ktor.user.model.request

data class GetUsersWithAwardsFullRequest(
	val companyId: String? = null,
	val filter: String? = null
)