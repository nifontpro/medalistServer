package ru.medals.ktor.award.model.request

data class AwardUserDeleteRequest(
	val awardId: String? = null,
	val userId: String? = null,
)
