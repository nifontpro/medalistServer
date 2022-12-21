package ru.medals.ktor.message.mappers

import ru.medals.domain.message.bussines.context.MessageCommand
import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.model.Message
import ru.medals.ktor.message.model.request.GetMessageByUserRequest
import ru.medals.ktor.message.model.request.MarkMessageAsReadRequest
import ru.medals.ktor.message.model.request.SendMessageRequest

fun MessageContext.fromTransport(request: SendMessageRequest) {
	command = MessageCommand.SEND
	message = Message(
		toId = request.toUserId ?: "",
		text = request.text ?: ""
	)
}

fun MessageContext.fromTransport(request: GetMessageByUserRequest) {
	command = MessageCommand.GET_BY_USER
	userId = request.userId
}

fun MessageContext.fromTransport(request: MarkMessageAsReadRequest) {
	command = MessageCommand.MARK_READ
	messageId = request.messageId
}