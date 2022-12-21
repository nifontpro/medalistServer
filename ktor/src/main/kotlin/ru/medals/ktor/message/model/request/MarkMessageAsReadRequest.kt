package ru.medals.ktor.message.model.request

data class MarkMessageAsReadRequest(
	val messageId: String = ""
)