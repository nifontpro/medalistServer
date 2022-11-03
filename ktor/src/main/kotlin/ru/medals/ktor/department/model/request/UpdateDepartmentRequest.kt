package ru.medals.ktor.department.model.request

data class UpdateDepartmentRequest(
	val id: String? = null,
	val name: String? = null,
	val description: String? = null,
)