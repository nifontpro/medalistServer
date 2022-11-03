package ru.medals.data.message.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.message.model.Message
import java.util.*

data class MessageCol(
	val fromId: String,
	val toId: String,
	val text: String,
	val read: Boolean, // Сообщение прочитано
	val timestamp: Date = Date(),

	@BsonId
	val id: String = ObjectId().toString()
) {

	fun toMessage() = Message(
		fromId = fromId,
		toId = toId,
		text = text,
		read = read,
		timestamp = timestamp.time,
		id = id
	)
}

fun Message.toMessageCol() = MessageCol(
	fromId = fromId,
	toId = toId,
	text = text,
	read = read,
	id = id
)