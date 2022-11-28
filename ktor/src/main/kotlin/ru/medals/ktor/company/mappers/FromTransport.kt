package ru.medals.ktor.company.mappers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.company.model.Company
import ru.medals.ktor.company.model.request.*

@Suppress("UNUSED_PARAMETER")
fun CompanyContext.fromTransport(request: CreateEmptyCompanyRequest) {
	command = CompanyContext.Command.CREATE_EMPTY
}

fun CompanyContext.fromTransport(request: CreateCompanyRequest) {
	command = CompanyContext.Command.CREATE
	company = Company(
		name = request.name ?: "",
		description = request.description,
		phone = request.phone,
		email = request.email,
		address = request.address
	)
}

fun CompanyContext.fromTransport(request: DeleteCompanyRequest) {
	command = CompanyContext.Command.DELETE
	companyId = request.companyId
}

@Suppress("UNUSED_PARAMETER")
fun CompanyContext.fromTransport(request: GetAllCompanyRequest) {
	command = CompanyContext.Command.GET_ALL
}

fun CompanyContext.fromTransport(request: GetCompanyByIdRequest) {
	command = CompanyContext.Command.GET_BY_ID
	companyId = request.companyId
}

fun CompanyContext.fromTransport(request: GetCompaniesByOwnerRequest) {
	command = CompanyContext.Command.GET_BY_OWNER
	searchFilter = request.filter
}

@Suppress("UNUSED_PARAMETER")
fun CompanyContext.fromTransport(request: GetCompanyCountRequest) {
	command = CompanyContext.Command.GET_COUNT_BY_OWNER
}

fun CompanyContext.fromTransport(request: UpdateCompanyRequest) {
	command = CompanyContext.Command.UPDATE
	companyId = request.id

	company = Company(
		id = request.id ?: "",
		name = request.name ?: "",
		description = request.description,
		phone = request.phone,
		email = request.email,
		address = request.address,
	)
}

fun CompanyContext.fromTransport(request: DeleteCompanyImageRequest) {
	command = CompanyContext.Command.IMAGE_DELETE
	companyId = request.companyId
	imageKey = request.imageKey
}