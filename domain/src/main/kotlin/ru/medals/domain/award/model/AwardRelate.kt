package ru.medals.domain.award.model

data class AwardRelate(
	val userId: String = "",
	val state: AwardState = AwardState.NONE,
	val date: Long? = null,
	val fromUserId: String = "",
)
