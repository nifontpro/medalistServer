package ru.medals.domain.activity.model

import ru.medals.domain.award.model.AwardLite
import ru.medals.domain.user.model.UserLite

data class ActivityExt(
	val user: UserLite? = null,
	val award: AwardLite? = null,
	val companyId: String = "",

	val state: ActivityState = ActivityState.NONE,
	val date: Long = 0,
	val departmentName: String? = null,

	val id: String = ""
)