package ru.medals.data.reward.model

import org.bson.codecs.pojo.annotations.BsonId
import ru.medals.data.medal.model.MedalCol
import ru.medals.domain.reward.model.Reward
import ru.medals.domain.reward.model.RewardState
import ru.medals.domain.reward.model.Signature

data class RewardMedal(
	val name: String = "",
	val description: String? = null,
	val score: Int = 0,
	val dateNominee: Long? = null,
	val dateActive: Long? = null,
	val dateInactive: Long? = null,
	val state: RewardState = RewardState.NOMINEE,
	val userId: String = "",
	val medal: MedalCol? = null,
	val sourceId: String = "",
	val companyId: String = "",
	val departmentId: String? = null,
	val signatures: List<Signature> = emptyList(),

	@BsonId
	val id: String = ""
) {
	fun toReward() = Reward(
		name = name,
		description = description,
		score = score,
		dateNominee = dateNominee,
		dateActive = dateActive,
		dateInactive = dateInactive,
		state = state,
		userId = userId,
		medal = medal?.toMedal(),
		sourceId = sourceId,
		companyId = companyId,
		departmentId = departmentId,
		signatures = signatures,
		id = id,
	)
}
