package ru.medals.domain.reward.model

data class RewardInfo(
	val reward: Reward = Reward(),
	val mncSignatures: List<MncSignature> = emptyList(),
	val allSignatures: Boolean = false
)

data class MncSignature(
	val mncId: String,
	val sign: Boolean = false,
	val dateSign: Long?,
	val name: String,
	val patronymic: String?,
	val lastname: String?
)
