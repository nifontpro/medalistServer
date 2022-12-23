package ru.medals.ktor.message.model.request

data class SendMessageRequest(
	val toUserId: String? = null,
	val theme: String? = null,
	val text: String? = null,
)
