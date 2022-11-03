package ru.medals.domain.department.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.department.model.Department
import ru.medals.domain.image.model.FileData

interface DepartmentRepository {

	suspend fun createDepartment(companyId: String): String?
	suspend fun deleteDepartment(department: Department): Boolean
	suspend fun getDepartmentById(id: String): Department?
	suspend fun getDepartmentsByCompany(companyId: String, filter: String? = null): List<Department>
	suspend fun doesOtherDepartmentWithName(name: String, companyId: String, departmentId: String): Boolean
	suspend fun updateDepartment(department: Department): Boolean
	suspend fun getDepartmentsCount(companyId: String): Long

	suspend fun updateImage(departmentId: String, fileData: FileData): Boolean

	suspend fun addImage(departmentId: String, fileData: FileData): RepositoryData<Unit>
	suspend fun updateImage(departmentId: String, imageKey: String, fileData: FileData): RepositoryData<Unit>
	suspend fun deleteImage(departmentId: String, imageKey: String): RepositoryData<Unit>
}