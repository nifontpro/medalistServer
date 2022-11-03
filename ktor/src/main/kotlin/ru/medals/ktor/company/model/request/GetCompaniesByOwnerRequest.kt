package ru.medals.ktor.company.model.request

data class GetCompaniesByOwnerRequest(
	val filter: String? = null
)