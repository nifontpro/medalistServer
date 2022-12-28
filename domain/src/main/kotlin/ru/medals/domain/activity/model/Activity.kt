package ru.medals.domain.activity.model

data class Activity(
	val userId: String = "",
	val awardId: String = "",
	val companyId: String? = null,

	val state: ActivityState = ActivityState.NONE,
	val date: Long = 0,

	val id: String = ""
)
