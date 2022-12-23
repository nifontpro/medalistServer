package ru.medals.ktor.message

import io.ktor.server.application.*
import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.bussines.processor.MessageProcessor
import ru.medals.domain.message.model.Message
import ru.medals.ktor.core.authProcess
import ru.medals.ktor.message.mappers.fromTransport
import ru.medals.ktor.message.mappers.toTransportGetMessage
import ru.medals.ktor.message.mappers.toTransportGetMessages
import ru.medals.ktor.message.model.request.*

suspend fun ApplicationCall.sendMessage(processor: MessageProcessor) =
	authProcess<SendMessageRequest, Message, MessageContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetMessage() }
	)

suspend fun ApplicationCall.deleteMessage(processor: MessageProcessor) =
	authProcess<DeleteMessageRequest, Message, MessageContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetMessage() }
	)

suspend fun ApplicationCall.getMessageByUser(processor: MessageProcessor) =
	authProcess<GetMessageByUserRequest, List<Message>, MessageContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetMessages() }
	)

suspend fun ApplicationCall.markMessageAsRead(processor: MessageProcessor) =
	authProcess<MarkMessageAsReadRequest, Unit, MessageContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.markMessageAsUnread(processor: MessageProcessor) =
	authProcess<MarkMessageAsUnreadRequest, Unit, MessageContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)
