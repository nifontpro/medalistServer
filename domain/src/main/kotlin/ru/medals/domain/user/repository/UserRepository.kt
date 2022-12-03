package ru.medals.domain.user.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserAwards
import ru.medals.domain.user.model.UserMedals

interface UserRepository {

	suspend fun createUser(user: User): RepositoryData<User>
	suspend fun getUserById(id: String): User?
	suspend fun getUserByEmail(email: String): User?
	suspend fun getUserByLogin(login: String): User?
	suspend fun getUsersByDepartment(departmentId: String, filter: String? = null): List<User>
	suspend fun updateProfile(user: User): Boolean
	suspend fun getDirectorDepartment(departmentId: String): User?
	suspend fun delete(user: User): RepositoryData<User>
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
	suspend fun addMedal(userId: String, medalId: String): RepositoryData<Unit>
	suspend fun getUserByIdWithMedals(userId: String): RepositoryData<UserMedals>
	suspend fun getUserByIdWithMedalsFilter(userId: String, medalId: String): RepositoryData<List<UserMedals>>
	suspend fun verifyUserByEmailExist(email: String): Boolean
	suspend fun verifyUserByLoginExist(login: String): Boolean
	suspend fun getUsersByCompanyWithDepartmentName(companyId: String, filter: String?): List<User>
	suspend fun checkExist(userId: String): RepositoryData<Unit>
	suspend fun getUsersByCompany(companyId: String, filter: String?): List<User>
	suspend fun getUserByIdWithDepartmentName(id: String): User?
	suspend fun updateAwardCount(userId: String, dCount: Int): RepositoryData<Unit>
	suspend fun calculateAwardCountOfUsers()
	suspend fun getUsersByCompanyWithAwards(companyId: String, filter: String? = null): RepositoryData<List<UserAwards>>
}