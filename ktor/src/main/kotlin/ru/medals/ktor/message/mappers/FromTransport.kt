package ru.medals.ktor.message.mappers

import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.model.Message
import ru.medals.ktor.message.model.request.SendMessageRequest

fun MessageContext.fromTransport(request: SendMessageRequest) {
	command = MessageContext.Command.SEND
	message = Message(
		toId = request.toUserId ?: "",
		text = request.text ?: ""
	)
}