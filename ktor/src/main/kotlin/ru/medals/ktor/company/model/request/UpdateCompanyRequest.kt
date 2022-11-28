package ru.medals.ktor.company.model.request

data class UpdateCompanyRequest(
	val id: String? = null,
	val name: String? = null,
	val description: String? = null,
	val phone: String? = null,
	val email: String? = null,
	val address: String? = null,
)