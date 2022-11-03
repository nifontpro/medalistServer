package ru.medals.domain.medal.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData
import ru.medals.domain.medal.model.Medal

interface MedalRepository {
	suspend fun createEmptyMedal(isSystem: Boolean, companyId: String?): String?
	suspend fun deleteMedal(medal: Medal): Boolean
	suspend fun getMedalById(id: String): Medal?
	suspend fun getCompanyMedals(companyId: String, filter: String?): List<Medal>
	suspend fun updateMedal(medal: Medal): Boolean
	suspend fun getCountByCompany(companyId: String): Long

	suspend fun updateImage(medalId: String, fileData: FileData): Boolean

	suspend fun addImage(medalId: String, fileData: FileData): RepositoryData<Unit>
	suspend fun updateImage(medalId: String, imageKey: String, fileData: FileData): RepositoryData<Unit>
	suspend fun deleteImage(medalId: String, imageKey: String): RepositoryData<Unit>
}