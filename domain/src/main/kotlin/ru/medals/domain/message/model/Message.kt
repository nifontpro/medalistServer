package ru.medals.domain.message.model

import java.util.*

data class Message(
	val fromId: String? = null,
	val toId: String = "",
	val type: MessageType = MessageType.NONE,
	val theme: String? = null,
	val themeSlug: String? = null,
	val text: String = "",
	val read: Boolean = false, // Сообщение прочитано
	val sendDate: Long? = null,
	val readDate: Long? = null,

	val id: String = ""
)

@Suppress("unused")
enum class MessageType {
	NONE, SYSTEM, USER
}