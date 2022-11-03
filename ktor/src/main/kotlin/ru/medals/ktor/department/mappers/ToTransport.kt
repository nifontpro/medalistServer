package ru.medals.ktor.department.mappers

import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.medals.domain.department.model.Department

fun DepartmentContext.toTransportGetById(): Department = department

fun DepartmentContext.toTransportGetByCompany(): List<Department> = departments