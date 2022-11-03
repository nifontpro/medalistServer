package ru.medals.ktor.company.model.request

data class GetAllCompanyRequest(
	val filter: String? = null
)