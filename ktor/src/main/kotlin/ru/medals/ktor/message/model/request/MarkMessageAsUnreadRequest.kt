package ru.medals.ktor.message.model.request

data class MarkMessageAsUnreadRequest(
	val messageId: String = ""
)