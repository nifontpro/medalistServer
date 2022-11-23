package ru.medals.ktor.department.model.request

data class CreateDepartmentRequest(
	val companyId: String? = null,
	val name: String? = null,
	val description: String? = null
)