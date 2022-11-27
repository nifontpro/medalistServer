package ru.medals.domain.award.repository

import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.award.model.AwardUsers
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData

interface AwardRepository {
	suspend fun create(award: Award): RepositoryData<Award>
	suspend fun delete(id: String): RepositoryData<Award>
	suspend fun getById(id: String): RepositoryData<Award>
	suspend fun getByCompany(companyId: String, filter: String?): RepositoryData<List<Award>>
	suspend fun getAwardsWithUsers(companyId: String, filter: String?): RepositoryData<List<AwardUsers>>
	suspend fun getByIdWithUser(awardId: String): RepositoryData<AwardUsers>
	suspend fun update(award: Award): RepositoryData<Unit>
	suspend fun updateImage(awardId: String, fileData: FileData): Boolean
	suspend fun awardUser(awardId: String, userId: String, fromUserId: String, state: AwardState)

}