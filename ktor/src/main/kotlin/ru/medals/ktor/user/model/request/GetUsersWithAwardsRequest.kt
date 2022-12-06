package ru.medals.ktor.user.model.request

data class GetUsersWithAwardsRequest(
	val companyId: String? = null,
	val filter: String? = null
)