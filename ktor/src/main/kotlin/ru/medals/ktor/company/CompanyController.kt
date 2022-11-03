package ru.medals.ktor.company

import io.ktor.server.application.*
import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.company.bussines.processor.CompanyProcessor
import ru.medals.domain.company.model.Company
import ru.medals.domain.core.response.IdResponse
import ru.medals.domain.core.response.baseResponseId
import ru.medals.ktor.company.mappers.fromTransport
import ru.medals.ktor.company.mappers.toTransportGetAllCompany
import ru.medals.ktor.company.mappers.toTransportGetCompanyById
import ru.medals.ktor.company.model.request.*
import ru.medals.ktor.core.*

suspend fun ApplicationCall.createCompany(processor: CompanyProcessor) =
	authProcess<CreateCompanyRequest, IdResponse, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { baseResponseId() }
	)

suspend fun ApplicationCall.deleteCompany(processor: CompanyProcessor) =
	authProcess<DeleteCompanyRequest, IdResponse, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.getAllCompany(processor: CompanyProcessor) =
	process<GetAllCompanyRequest, List<Company>, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAllCompany() }
	)

suspend fun ApplicationCall.getCompanyById(processor: CompanyProcessor) =
	process<GetCompanyByIdRequest, Company, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCompanyById() }
	)

suspend fun ApplicationCall.getCompaniesByOwner(processor: CompanyProcessor) =
	authProcess<GetCompaniesByOwnerRequest, List<Company>, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAllCompany() }
	)

suspend fun ApplicationCall.updateCompanyProfile(processor: CompanyProcessor) =
	authProcess<UpdateCompanyRequest, Unit, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.updateCompanyImageOld(processor: CompanyProcessor) {
	val context = CompanyContext().apply { command = CompanyContext.Command.UPDATE_IMAGE }
	processImageSingle(context = context, processor = processor)
}

suspend fun ApplicationCall.getCountByOwner(processor: CompanyProcessor) =
	authProcess<GetCompanyCountRequest, Long, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCount() }
	)

suspend fun ApplicationCall.createCompanyImage(processor: CompanyProcessor) {
	val context = CompanyContext().apply { command = CompanyContext.Command.IMAGE_ADD }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.updateCompanyImage(processor: CompanyProcessor) {
	val context = CompanyContext().apply { command = CompanyContext.Command.IMAGE_UPDATE }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.deleteCompanyImage(processor: CompanyProcessor) =
	authProcess<DeleteCompanyImageRequest, Unit, CompanyContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) }
	)
