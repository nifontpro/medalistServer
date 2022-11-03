package ru.medals.domain.message.repository

import ru.medals.domain.message.model.Message

interface MessageRepository {
	suspend fun insert(message: Message): String?
	suspend fun delete(messageId: String): Boolean
	suspend fun getByUser(userId: String): List<Message>
	suspend fun markAsRead(messageId: String): Boolean
}