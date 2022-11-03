package ru.medals.data.reward.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.medal.model.Medal
import ru.medals.domain.reward.model.Reward
import ru.medals.domain.reward.model.RewardState
import ru.medals.domain.reward.model.Signature

data class RewardCol(
	val name: String,
	val description: String? = null,
	val score: Int = 0,
	val dateNominee: Long? = null,
	val dateActive: Long? = null,
	val dateInactive: Long? = null,
	val state: RewardState = RewardState.NOMINEE,
	val userId: String,
	val medalId: String,
	val sourceId: String,
	val companyId: String,
	val departmentId: String? = null,
	val signatures: List<Signature> = listOf(),

	@BsonId
	val id: String = ObjectId().toString()
) {

	@Suppress("unused")
	fun toReward(medal: Medal?) = Reward(
		name = name,
		description = description,
		score = score,
		dateNominee = dateNominee,
		dateActive = dateActive,
		dateInactive = dateInactive,
		state = state,
		userId = userId,
		sourceId = sourceId,
		companyId = companyId,
		departmentId = departmentId,
		signatures = signatures,
		medal = medal,
		id = id
	)
}