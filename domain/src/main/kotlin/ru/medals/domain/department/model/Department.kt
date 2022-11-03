package ru.medals.domain.department.model

data class Department(
	val name: String = "",
	val description: String? = null,
	val imageUrl: String? = null,
	val companyId: String = "", // id компании

	val id: String = ""
)
