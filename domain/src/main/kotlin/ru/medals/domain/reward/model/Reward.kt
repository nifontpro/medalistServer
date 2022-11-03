package ru.medals.domain.reward.model

import ru.medals.domain.medal.model.Medal

data class Reward(
	val name: String = "",
	val description: String? = null,
	val score: Int = 0,
	val dateNominee: Long? = null,
	val dateActive: Long? = null,
	val dateInactive: Long? = null,
	val state: RewardState = RewardState.NOMINEE,
	val userId: String = "",
	val medal: Medal? = null,
	val sourceId: String = "",
	val companyId: String = "",
	val departmentId: String? = null,
	val signatures: List<Signature> = listOf(),
	val id: String = ""
)

@Suppress("unused")
enum class RewardState {
	NOMINEE,
	ACTIVE,
	INACTIVE,
}

/**
 * Подписи членов номинационной комиссии
 */
data class Signature(
	val mncId: String,
	val date: Long
)

