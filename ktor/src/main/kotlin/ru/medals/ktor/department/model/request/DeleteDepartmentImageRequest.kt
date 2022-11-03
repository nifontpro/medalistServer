package ru.medals.ktor.department.model.request

data class DeleteDepartmentImageRequest(
	val departmentId: String? = null,
	val imageKey: String? = null,
)