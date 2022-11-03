package ru.medals.ktor.department.model.request

data class GetDepartmentsByCompanyRequest(
	val companyId: String? = null,
	val filter: String? = null
)