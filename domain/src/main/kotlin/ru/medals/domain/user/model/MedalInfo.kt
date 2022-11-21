package ru.medals.domain.user.model

import ru.medals.domain.medal.model.Medal

data class MedalInfo(
	val medal: Medal = Medal(),
	val createDate: Long = 0,
	val score: Int = 0,
	val fromUserId: String = "",
)