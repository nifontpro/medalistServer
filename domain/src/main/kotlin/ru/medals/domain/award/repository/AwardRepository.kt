package ru.medals.domain.award.repository

import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardMedal
import ru.medals.domain.core.bussines.model.RepositoryData

interface AwardRepository {
	suspend fun create(award: Award): RepositoryData<Award>
	suspend fun delete(id: String): RepositoryData<Award>
	suspend fun getById(id: String): RepositoryData<Award>
	suspend fun getByFilter(companyId: String, filter: String?): RepositoryData<List<Award>>
	suspend fun update(award: Award): RepositoryData<Unit>
	suspend fun getAwardByIdWithMedal(id: String): RepositoryData<AwardMedal>
	suspend fun getAwardsByFilterMedal(companyId: String, filter: String? = null): RepositoryData<List<AwardMedal>>
}