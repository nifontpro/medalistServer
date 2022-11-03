package ru.medals.domain.message.model

data class Message(
	val fromId: String = "",
	val toId: String = "",
	val text: String = "",
	val read: Boolean = false, // Сообщение прочитано
	val timestamp: Long = -1,

	val id: String = ""
)