package ru.medals.domain.activity.model

import ru.medals.domain.award.model.AwardLite
import ru.medals.domain.user.model.User

data class ActivityExt(
	val user: User = User(),
	val award: AwardLite = AwardLite(),
	val companyId: String? = null,

	val state: ActivityState = ActivityState.NONE,
	val date: Long = 0,
	val departmentName: String? = null,

	val id: String = ""
)