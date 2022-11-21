package ru.medals.ktor.department.mappers

import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.medals.domain.department.model.Department

fun DepartmentContext.toTransportGetDepartment(): Department = department

fun DepartmentContext.toTransportGetDepartments(): List<Department> = departments