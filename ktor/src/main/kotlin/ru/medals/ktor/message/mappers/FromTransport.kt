package ru.medals.ktor.message.mappers

import ru.medals.domain.message.bussines.context.MessageCommand
import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.model.MessageType
import ru.medals.ktor.message.model.request.*

fun MessageContext.fromTransport(request: SendMessageRequest) {
	command = MessageCommand.SEND
	userId = request.toUserId
	message = Message(
		theme = request.theme,
		text = request.text ?: "",
		type = MessageType.USER
	)
}

fun MessageContext.fromTransport(request: DeleteMessageRequest) {
	command = MessageCommand.DELETE
	messageId = request.messageId
}

fun MessageContext.fromTransport(request: GetMessageByUserRequest) {
	command = MessageCommand.GET_BY_USER
	userId = request.userId
}

fun MessageContext.fromTransport(request: MarkMessageAsReadRequest) {
	command = MessageCommand.MARK_READ
	messageId = request.messageId
}

fun MessageContext.fromTransport(request: MarkMessageAsUnreadRequest) {
	command = MessageCommand.MARK_UNREAD
	messageId = request.messageId
}
