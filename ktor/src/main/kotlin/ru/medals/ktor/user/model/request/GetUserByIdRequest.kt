package ru.medals.ktor.user.model.request

data class GetUserByIdRequest(
	val userId: String? = null
)