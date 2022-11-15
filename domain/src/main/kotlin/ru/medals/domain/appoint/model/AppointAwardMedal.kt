package ru.medals.domain.appoint.model

import ru.medals.domain.award.model.AwardMedal
import java.util.*

data class AppointAwardMedal(
	val awardMedal: AwardMedal,
	val userId: String,
	val status: AppointStatus = AppointStatus.NONE,
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	val id: String
)