package ru.medals.domain.award.repository

import ru.medals.domain.award.model.Award
import ru.medals.domain.core.bussines.model.RepositoryData

interface AwardRepository {
	suspend fun create(award: Award): String?
	suspend fun delete(id: String): RepositoryData<Award>
}