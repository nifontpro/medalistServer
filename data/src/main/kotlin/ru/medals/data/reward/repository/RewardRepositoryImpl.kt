package ru.medals.data.reward.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import ru.medals.data.reward.model.RewardCol
import ru.medals.data.reward.model.RewardMedalCol
import ru.medals.data.user.model.UserCol
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError
import ru.medals.domain.reward.model.Nominee
import ru.medals.domain.reward.model.Reward
import ru.medals.domain.reward.model.RewardState
import ru.medals.domain.reward.model.Signature
import ru.medals.domain.reward.repository.RewardRepository

class RewardRepositoryImpl(
	db: CoroutineDatabase,
	private val client: CoroutineClient
) : RewardRepository {

	private val users = db.getCollection<UserCol>()
	private val rewards = db.getCollection<RewardCol>()

	/**
	 * Номинировать сотрудника на награду
	 * sourceId - id того кто награждает (источника)
	 */
	override suspend fun nomineeUser(
		nominee: Nominee,
		sourceId: String,
		departmentId: String?
	): String? {

		val rewardCol = RewardCol(
			state = RewardState.NOMINEE,
			name = nominee.name,
			description = nominee.description,
			score = nominee.score,
			dateNominee = System.currentTimeMillis(),
			userId = nominee.userId,
			medalId = nominee.medalId,
			sourceId = sourceId,
			companyId = nominee.companyId,
			departmentId = departmentId
		)
		return if (rewards.insertOne(rewardCol).wasAcknowledged()) {
			rewardCol.id
		} else {
			null
		}
	}

	override suspend fun rewardUser(rewardId: String): RepositoryData<Unit> {

		client.startSession().use { clientSession ->
			clientSession.startTransaction()
			val reward = rewards.findOneById(rewardId) ?: run {
				clientSession.abortTransactionAndAwait()
				return RepositoryData.error(
					error = RepositoryError(
						repository = "reward",
						violationCode = "reward not found",
						description = "Награждение не найдено"
					)
				)
			}

			rewards.updateOneById(
				rewardId, set(
					RewardCol::state setTo RewardState.ACTIVE,
					RewardCol::dateActive setTo System.currentTimeMillis()
				)
			)

			val user = users.findOneById(reward.userId) ?: run {
				clientSession.abortTransactionAndAwait()
				return RepositoryData.error(
					error = RepositoryError(
						repository = "reward",
						violationCode = "user not found",
						description = "Сотрудник, указанный в награде, не найден"
					)
				)
			}

			users.updateOneById(
				reward.userId,
				UserCol(
					id = reward.userId,
					currentScore = 0,
					score = (user.score ?: 0) + reward.score,
					rewardCount = (user.rewardCount ?: 0) + 1
				)
			)
			clientSession.commitTransactionAndAwait()
			return RepositoryData.success()
		}
	}

	// db.rewardCol.aggregate([{$lookup: {from:"medalCol", localField:"medalId",foreignField:"_id", as: "medals"}}, {$project: {name:1, medal: {$first: "$medals"}}}]).pretty()
	override suspend fun getRewardsByUser(userId: String): List<Reward> {
		return rewards.aggregate<RewardMedalCol>(
			"[" +
							"{\$match : {userId : {\$eq: \"$userId\"}}}" +
							"{\$lookup: {from:\"medalCol\", localField:\"medalId\",foreignField:\"_id\", as: \"medals\"}}, " +
							"{\$project: {medal: {\$first: \"\$medals\"}, name:1, description:1, score:1, dateNominee:1, dateActive:1, dateInactive:1, state:1, userId:1, sourceId:1, companyId:1, departmentId:1, signatures: 1}}" +
							"]"
		)
			.toList().map {
				it.toReward()
			}

	}

	override suspend fun getRewardById(rewardId: String): Reward? {
//		val reward = rewards.findOneById(rewardId)
//		return reward?.toReward(medals.findOne(MedalCol::id eq reward.medalId)?.toMedal())

		return rewards.aggregate<RewardMedalCol>(
			"[" +
							"{\$match : {_id : {\$eq: \"$rewardId\"}}}" +
							"{\$lookup: {from:\"medalCol\", localField:\"medalId\",foreignField:\"_id\", as: \"medals\"}}, " +
							"{\$project: {medal: {\$first: \"\$medals\"}, name:1, description:1, score:1, dateNominee:1, dateActive:1, dateInactive:1, state:1, userId:1, sourceId:1, companyId:1, departmentId:1, signatures: 1}}" +
							"]"
		)
			.toList().firstOrNull()?.toReward()
	}

	override suspend fun getCountByCompany(companyId: String): Long {
		return rewards.countDocuments(RewardCol::companyId eq companyId)
	}

	/**
	 * Поставить подпись MNC в номинируемой награде
	 */
	override suspend fun putSignature(rewardId: String, mncId: String): Boolean {
		return rewards.updateOneById(
			rewardId,
			addToSet(RewardCol::signatures, Signature(mncId = mncId, System.currentTimeMillis())),
//			push(RewardCol::signatures, Signature(mncId = mncId, System.currentTimeMillis())) // Добавляет, даже если есть
		).wasAcknowledged()
	}

	/**
	 * Удалить подпись MNC в номинируемой награде
	 */
	override suspend fun deleteSignature(rewardId: String, mncId: String): Boolean {
		return rewards.updateOneById(
			rewardId,
			pullByFilter(RewardCol::signatures, Signature::mncId eq mncId),
		).wasAcknowledged()
	}
}