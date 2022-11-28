package ru.medals.ktor.award.model.request

import ru.medals.domain.award.model.AwardState

data class AwardUserRequest(
	val awardId: String? = null,
	val userId: String? = null,
	val awardState: AwardState = AwardState.NONE
)
