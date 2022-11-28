package ru.medals.domain.award.model

data class AwardRelate(
	val userId: String = "",
	val state: AwardState = AwardState.NONE,
	val nomineeDate: Long? = null,
	val awardDate: Long? = null,
	val nomineeUserId: String? = null,
	val awardUserId: String? = null,
)
