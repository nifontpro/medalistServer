package ru.medals.ktor.department

import io.ktor.server.application.*
import ru.medals.domain.core.response.IdResponse
import ru.medals.domain.core.response.baseResponseId
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.medals.domain.department.bussines.processor.DepartmentProcessor
import ru.medals.domain.department.model.Department
import ru.medals.ktor.award.model.request.GetIdsRequest
import ru.medals.ktor.core.*
import ru.medals.ktor.department.mappers.fromTransport
import ru.medals.ktor.department.mappers.toTransportGetDepartment
import ru.medals.ktor.department.mappers.toTransportGetDepartments
import ru.medals.ktor.department.model.request.*

suspend fun ApplicationCall.createEmptyDepartment(processor: DepartmentProcessor) =
	authProcess<CreateEmptyDepartmentRequest, IdResponse, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { baseResponseId() }
	)

suspend fun ApplicationCall.createDepartment(processor: DepartmentProcessor) =
	authProcess<CreateDepartmentRequest, Department, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetDepartment() }
	)

suspend fun ApplicationCall.deleteDepartment(processor: DepartmentProcessor) =
	authProcess<DeleteDepartmentRequest, Department, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetDepartment() }
	)

suspend fun ApplicationCall.getDepartmentById(processor: DepartmentProcessor) =
	process<GetDepartmentByIdRequest, Department, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetDepartment() }
	)

suspend fun ApplicationCall.getDepartmentsByCompany(processor: DepartmentProcessor) =
	process<GetDepartmentsByCompanyRequest, List<Department>, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetDepartments() }
	)

suspend fun ApplicationCall.getDepartmentCount(processor: DepartmentProcessor) =
	process<GetDepartmentsCountRequest, Long, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCount() }
	)

suspend fun ApplicationCall.departmentUpdate(processor: DepartmentProcessor) =
	authProcess<UpdateDepartmentRequest, Unit, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.updateDepartmentImageOld(processor: DepartmentProcessor) {
	val context = DepartmentContext().apply { command = DepartmentContext.Command.UPDATE_IMAGE_OLD }
	processImageSingle(context = context, processor = processor)
}

suspend fun ApplicationCall.createDepartmentImage(processor: DepartmentProcessor) {
	val context = DepartmentContext().apply { command = DepartmentContext.Command.IMAGE_ADD }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.updateDepartmentImage(processor: DepartmentProcessor) {
	val context = DepartmentContext().apply { command = DepartmentContext.Command.IMAGE_UPDATE }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.deleteDepartmentImage(processor: DepartmentProcessor) =
	authProcess<DeleteDepartmentImageRequest, Unit, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) }
	)

suspend fun ApplicationCall.getDepartmentIds(processor: DepartmentProcessor) =
	process<GetIdsRequest, List<String>, DepartmentContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetIds() }
	)