package ru.medals.ktor.company.model.request

data class DeleteCompanyImageRequest(
	val companyId: String? = null,
	val imageKey: String? = null,
)