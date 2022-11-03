package ru.medals.data.message.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import ru.medals.data.message.model.MessageCol
import ru.medals.data.message.model.toMessageCol
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.repository.MessageRepository

class MessageRepositoryImpl(
	db: CoroutineDatabase
) : MessageRepository {

	private val messages = db.getCollection<MessageCol>()

	override suspend fun insert(message: Message): String? {
		val messageCol = message.toMessageCol()
		return if (messages.insertOne(messageCol).wasAcknowledged()) {
			messageCol.id
		} else {
			null
		}
	}

	override suspend fun delete(messageId: String): Boolean {
		return messages.deleteOneById(messageId).wasAcknowledged()
	}

	override suspend fun getByUser(userId: String): List<Message> {
		return messages.find(MessageCol::toId eq userId)
			.ascendingSort(MessageCol::timestamp)
			.toList().map { it.toMessage() }
	}

	override suspend fun markAsRead(messageId: String): Boolean {
		return messages.updateOneById(id = messageId, set(MessageCol::read setTo true)).wasAcknowledged()
	}
}