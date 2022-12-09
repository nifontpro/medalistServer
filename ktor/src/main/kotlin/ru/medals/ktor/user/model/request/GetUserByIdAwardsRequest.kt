package ru.medals.ktor.user.model.request

data class GetUserByIdAwardsRequest(
	val userId: String? = null
)