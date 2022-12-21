package ru.medals.ktor.company.mappers

import ru.medals.domain.company.bussines.context.CompanyCommand
import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.company.model.Company
import ru.medals.ktor.company.model.request.*

@Suppress("UNUSED_PARAMETER")
fun CompanyContext.fromTransport(request: CreateEmptyCompanyRequest) {
	command = CompanyCommand.CREATE_EMPTY
}

fun CompanyContext.fromTransport(request: CreateCompanyRequest) {
	command = CompanyCommand.CREATE
	company = Company(
		name = request.name ?: "",
		description = request.description,
		phone = request.phone,
		email = request.email,
		address = request.address
	)
}

fun CompanyContext.fromTransport(request: DeleteCompanyRequest) {
	command = CompanyCommand.DELETE
	companyId = request.companyId
}

@Suppress("UNUSED_PARAMETER")
fun CompanyContext.fromTransport(request: GetAllCompanyRequest) {
	command = CompanyCommand.GET_ALL
}

fun CompanyContext.fromTransport(request: GetCompanyByIdRequest) {
	command = CompanyCommand.GET_BY_ID
	companyId = request.companyId
}

fun CompanyContext.fromTransport(request: GetCompaniesByOwnerRequest) {
	command = CompanyCommand.GET_BY_OWNER
	searchFilter = request.filter
}

@Suppress("UNUSED_PARAMETER")
fun CompanyContext.fromTransport(request: GetCompanyCountRequest) {
	command = CompanyCommand.GET_COUNT_BY_OWNER
}

fun CompanyContext.fromTransport(request: UpdateCompanyRequest) {
	command = CompanyCommand.UPDATE
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
	command = CompanyCommand.IMAGE_DELETE
	companyId = request.companyId
	imageKey = request.imageKey
}

fun CompanyContext.fromTransport(request: DeleteCompanyMainImageRequest) {
	command = CompanyCommand.DELETE_MAIN_IMAGE
	companyId = request.companyId
}