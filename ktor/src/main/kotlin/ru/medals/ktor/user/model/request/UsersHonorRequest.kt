package ru.medals.ktor.user.model.request

data class UsersHonorRequest(
	val companyId: String? = null,
	val filter: String? = null,
	val startDate: Long? = null,
	val endDate: Long? = null,
	val count: Int? = null
)