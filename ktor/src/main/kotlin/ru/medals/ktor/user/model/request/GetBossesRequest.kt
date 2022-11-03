package ru.medals.ktor.user.model.request

data class GetBossesRequest(
	val companyId: String? = null,
	val filter: String? = null,
)