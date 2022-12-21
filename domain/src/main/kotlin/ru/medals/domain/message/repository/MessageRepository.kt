package ru.medals.domain.message.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.message.model.Message

interface MessageRepository {
	suspend fun insert(message: Message): RepositoryData<Message>
	suspend fun delete(messageId: String): RepositoryData<Unit>
	suspend fun getByUser(userId: String): RepositoryData<List<Message>>
	suspend fun markAsRead(messageId: String): RepositoryData<Unit>
	suspend fun markAsUnread(messageId: String): RepositoryData<Unit>
}