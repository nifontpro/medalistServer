package ru.medals.ktor.user.model.request

data class UserCountByDepartmentRequest(
	val departmentId: String? = null,
)