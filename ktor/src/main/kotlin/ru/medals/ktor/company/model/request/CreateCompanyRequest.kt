package ru.medals.ktor.company.model.request

data class CreateCompanyRequest(
	val ownerId: String? = null
)