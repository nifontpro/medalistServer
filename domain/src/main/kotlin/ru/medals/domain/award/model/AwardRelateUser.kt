package ru.medals.domain.award.model

import ru.medals.domain.user.model.User

data class AwardRelateUser(
	val user: User = User(),
	val state: AwardState = AwardState.NONE,
	val date: Long? = null,
	val fromUserId: String = "",
)
