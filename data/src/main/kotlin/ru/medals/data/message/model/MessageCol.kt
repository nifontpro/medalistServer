package ru.medals.data.message.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.model.MessageType
import java.util.*

data class MessageCol(
	val fromId: String? = null,
	val toId: String = "",
	val type: MessageType = MessageType.NONE,
	val theme: String? = null,
	val themeSlug: String? = null,
	val text: String = "",
	val read: Boolean = false, // Сообщение прочитано
	val sendDate: Date? = null,
	val readDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {

	fun toMessage() = Message(
		fromId = fromId,
		toId = toId,
		type = type,
		theme = theme,
		themeSlug = themeSlug,
		text = text,
		read = read,
		sendDate = sendDate?.time,
		readDate = readDate?.time,

		id = id
	)
}

fun Message.toMessageColCreate() = MessageCol(
	fromId = fromId,
	toId = toId,
	type = type,
	theme = theme,
	themeSlug = themeSlug,
	text = text,
)