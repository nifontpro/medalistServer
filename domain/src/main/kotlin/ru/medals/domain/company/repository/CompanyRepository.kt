package ru.medals.domain.company.repository

import ru.medals.domain.company.model.Company
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData

interface CompanyRepository {
	suspend fun createEmptyCompany(ownerId: String): String?
	suspend fun createCompany(company: Company): RepositoryData<Company>
	suspend fun updateCompanyProfile(company: Company): Boolean
	suspend fun deleteCompany(id: String): RepositoryData<Company>
	suspend fun getCompanyByName(name: String): Company?
	suspend fun getAll(): List<Company>
	suspend fun getCompaniesByOwnerId(ownerId: String, filter: String? = null): List<Company>
	suspend fun getCompanyById(id: String): Company?
	suspend fun doesCompanyByOwnerWithName(name: String, ownerId: String): Boolean
	suspend fun doesOtherCompanyByOwnerWithName(name: String, companyId: String, ownerId: String): Boolean
	suspend fun getCompanyCount(ownerId: String): Long

	suspend fun updateImage(companyId: String, fileData: FileData): Boolean

	suspend fun addImage(companyId: String, fileData: FileData): RepositoryData<Unit>
	suspend fun updateImage(companyId: String, imageKey: String, fileData: FileData): RepositoryData<Unit>
	suspend fun deleteImage(companyId: String, imageKey: String): RepositoryData<Unit>
	suspend fun deleteMainImage(companyId: String): RepositoryData<Unit>
}