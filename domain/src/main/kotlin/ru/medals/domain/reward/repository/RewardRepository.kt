package ru.medals.domain.reward.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.reward.model.Nominee
import ru.medals.domain.reward.model.Reward

interface RewardRepository {

	suspend fun nomineeUser(
		nominee: Nominee,
		sourceId: String,
		departmentId: String?
	): String?

	suspend fun rewardUser(rewardId: String): RepositoryData<Unit>
	suspend fun getRewardsByUser(userId: String): List<Reward>
	suspend fun getCountByCompany(companyId: String): Long
	suspend fun putSignature(rewardId: String, mncId: String): Boolean
	suspend fun getRewardById(rewardId: String): Reward?
	suspend fun deleteSignature(rewardId: String, mncId: String): Boolean
}