package ru.medals.ktor.message.model.request

data class GetMessageByUserRequest(
	val userId: String? = null
)