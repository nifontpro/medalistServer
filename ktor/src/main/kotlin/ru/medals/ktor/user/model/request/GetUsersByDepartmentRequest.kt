package ru.medals.ktor.user.model.request

data class GetUsersByDepartmentRequest(
	val departmentId: String? = null,
	val filter: String? = null
)