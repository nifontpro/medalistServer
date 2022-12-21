package ru.medals.data.message.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import ru.medals.data.message.model.MessageCol
import ru.medals.data.message.model.toMessageColCreate
import ru.medals.data.message.repository.MessageRepoErrors.Companion.errorMessageDelete
import ru.medals.data.message.repository.MessageRepoErrors.Companion.errorMessageGet
import ru.medals.data.message.repository.MessageRepoErrors.Companion.errorMessageWrite
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.repository.MessageRepository
import java.util.*

class MessageRepositoryImpl(
	db: CoroutineDatabase
) : MessageRepository {

	private val messages = db.getCollection<MessageCol>()

	override suspend fun insert(message: Message): RepositoryData<Message> {
		val messageCol = message.toMessageColCreate().copy(
			sendDate = Date(System.currentTimeMillis()),
			read = false
		)

		return try {
			messages.insertOne(messageCol)
			RepositoryData.success(data = messageCol.toMessage())
		} catch (e: Exception) {
			errorMessageWrite()
		}
	}

	override suspend fun delete(messageId: String): RepositoryData<Unit> {
		return try {
			messages.deleteOneById(messageId)
			RepositoryData.success()
		} catch (e: Exception) {
			errorMessageDelete()
		}
	}

	override suspend fun getByUser(userId: String): RepositoryData<List<Message>> {
		return try {
			val messages = messages.find(MessageCol::toId eq userId)
				.ascendingSort(MessageCol::sendDate)
				.toList().map { it.toMessage() }
			RepositoryData.success(data = messages)
		} catch (e: Exception) {
			errorMessageGet()
		}
	}

	override suspend fun markAsRead(messageId: String): RepositoryData<Unit> {
		return try {
			messages.updateOneById(
				id = messageId,
				set(
					MessageCol::read setTo true,
					MessageCol::readDate setTo Date(System.currentTimeMillis())
				)
			)
			RepositoryData.success()
		} catch (e: Exception) {
			errorMessageWrite()
		}
	}

	override suspend fun markAsUnread(messageId: String): RepositoryData<Unit> {
		return try {
			messages.updateOneById(
				id = messageId,
				set(
					MessageCol::read setTo false,
					MessageCol::readDate setTo null
				)
			)
			RepositoryData.success()
		} catch (e: Exception) {
			errorMessageWrite()
		}
	}


}