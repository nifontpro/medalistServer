package ru.medals.ktor.department.mappers

import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.medals.domain.department.model.Department
import ru.medals.ktor.department.model.request.*

fun DepartmentContext.fromTransport(request: CreateEmptyDepartmentRequest) {
	command = DepartmentContext.Command.CREATE_EMPTY
	companyId = request.companyId
}

fun DepartmentContext.fromTransport(request: CreateDepartmentRequest) {
	command = DepartmentContext.Command.CREATE
	department = Department(
		name = request.name ?: "",
		description = request.description,
		companyId = request.companyId ?: ""
	)
	companyId = request.companyId
}

fun DepartmentContext.fromTransport(request: DeleteDepartmentRequest) {
	command = DepartmentContext.Command.DELETE
	departmentId = request.departmentId
}

fun DepartmentContext.fromTransport(request: GetDepartmentByIdRequest) {
	command = DepartmentContext.Command.GET_BY_ID
	departmentId = request.departmentId
}

fun DepartmentContext.fromTransport(request: GetDepartmentsByCompanyRequest) {
	command = DepartmentContext.Command.GET_BY_COMPANY
	companyId = request.companyId
	searchFilter = request.filter
}

fun DepartmentContext.fromTransport(request: GetDepartmentsCountRequest) {
	command = DepartmentContext.Command.COUNT_BY_COMPANY
	companyId = request.companyId
}

fun DepartmentContext.fromTransport(request: UpdateDepartmentRequest) {
	command = DepartmentContext.Command.UPDATE
	department = Department(
		id = request.id ?: "",
		name = request.name ?: "",
		description = request.description,
	)
}

fun DepartmentContext.fromTransport(request: DeleteDepartmentImageRequest) {
	command = DepartmentContext.Command.IMAGE_DELETE
	departmentId = request.departmentId
	imageKey = request.imageKey
}