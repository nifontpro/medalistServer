package ru.medals.domain.award.repository

import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardUsers
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData

interface AwardRepository {
	suspend fun create(award: Award): RepositoryData<Award>
	suspend fun delete(id: String): RepositoryData<Award>
	suspend fun getById(id: String): RepositoryData<Award>
	suspend fun getByCompany(companyId: String, filter: String?): RepositoryData<List<Award>>
	suspend fun getAwardsWithUsers(companyId: String, filter: String?): RepositoryData<List<AwardUsers>>
	suspend fun getByIdWithUsers(awardId: String): RepositoryData<AwardUsers>
	suspend fun update(award: Award): RepositoryData<Unit>
	suspend fun updateImage(awardId: String, fileData: FileData): Boolean
	suspend fun getAwardRelateFromUser(awardId: String, userId: String): RepositoryData<AwardRelate>
	suspend fun awardUser(awardId: String, awardRelate: AwardRelate, isNew: Boolean): RepositoryData<Unit>
	suspend fun deleteUserAward(awardId: String, userId: String): RepositoryData<AwardRelate>
	suspend fun calculateAwardCountOfUser(userId: String): Long
}