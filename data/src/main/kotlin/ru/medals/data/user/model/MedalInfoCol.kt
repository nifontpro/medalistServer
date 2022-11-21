package ru.medals.data.user.model

import java.util.*

data class MedalInfoCol(
	val medalId: String,
	val createDate: Date,
	val score: Int = 0,
	val fromUserId: String = "",
)