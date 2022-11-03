package ru.medals.domain.user.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData
import ru.medals.domain.user.model.User

interface UserRepository {

	suspend fun createUser(user: User): String?
	suspend fun getUserById(id: String): User?
	suspend fun getUserByEmail(email: String): User?
	suspend fun getUserByLogin(login: String): User?
	suspend fun getUsersByDepartment(departmentId: String, filter: String? = null): List<User>
	suspend fun updateProfile(user: User): Boolean
	suspend fun getDirectorDepartment(departmentId: String): User?
	suspend fun delete(user: User): Boolean
	suspend fun getUsersCountByCompany(companyId: String): Long
	suspend fun getUsersCountByDepartment(departmentId: String): Long
	suspend fun getAllMnc(companyId: String): List<User>
	suspend fun getAllMncIds(companyId: String): List<String>
	suspend fun getBestUsersByCompany(companyId: String, limit: Int = Int.MAX_VALUE): List<User>
	suspend fun getCompanyAdmins(companyId: String, filter: String?): List<User>

	suspend fun updateImage(userId: String, fileData: FileData): Boolean

	suspend fun addImage(userId: String, fileData: FileData): RepositoryData<Unit>
	suspend fun updateImage(userId: String, imageKey: String, fileData: FileData): RepositoryData<Unit>
	suspend fun deleteImage(userId: String, imageKey: String): RepositoryData<Unit>
}