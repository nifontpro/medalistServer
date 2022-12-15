package ru.medals.ktor.user.model.request

data class UserAwardCountByDepartmentRequest(
	val departmentId: String? = null,
)