package ru.medals.domain.appoint.model

import java.util.*

data class Appoint(
	val awardId: String = "",
	val userId: String = "",
	val status: AppointStatus = AppointStatus.NONE,
	val images: List<String> = emptyList(),
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	val id: String
)

@Suppress("unused")
enum class AppointStatus {
	NONE, NOMINEE, AWARD
}