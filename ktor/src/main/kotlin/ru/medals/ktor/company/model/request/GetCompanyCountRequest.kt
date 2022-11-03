package ru.medals.ktor.company.model.request

data class GetCompanyCountRequest(
	val filter: String? = null
)