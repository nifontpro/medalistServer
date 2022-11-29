package ru.medals.domain.award.model

import ru.medals.domain.user.model.User

data class AwardRelateUser(
	val user: User = User(),
	val state: AwardState = AwardState.NONE,
	val nomineeDate: Long? = null,
	val awardDate: Long? = null,
	val fromNomineeUser: User? = null,
	val fromAwardUser: User? = null,
)
