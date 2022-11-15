package ru.medals.domain.appoint.model

import ru.medals.domain.user.model.User

data class AppointUser(
	val awardId: String,
	val user: User,
	val status: AppointStatus = AppointStatus.NONE,
	val images: List<String> = emptyList(),
	val nomineeDate: Long? = null,
	val rewardDate: Long? = null,

	val id: String
)