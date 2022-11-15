package ru.medals.domain.appoint.model

import ru.medals.domain.award.model.Award
import java.util.*

data class AppointAward(
	val award: Award,
	val userId: String,
	val status: AppointStatus = AppointStatus.NONE,
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	val id: String
)