package ru.medals.domain.appoint.model

data class Appoint(
	val awardId: String = "",
	val userId: String = "",
	val status: AppointStatus = AppointStatus.NONE,
	val images: List<String> = emptyList(),

	val id: String
)

@Suppress("unused")
enum class AppointStatus {
	NONE, NOMINEE, AWARD
}