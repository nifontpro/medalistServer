package ru.medals.ktor.message

import io.ktor.server.application.*
import ru.medals.domain.core.response.IdResponse
import ru.medals.domain.core.response.baseResponseId
import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.bussines.processor.MessageProcessor
import ru.medals.ktor.core.authProcess
import ru.medals.ktor.message.mappers.fromTransport
import ru.medals.ktor.message.model.request.SendMessageRequest

suspend fun ApplicationCall.sendMessage(processor: MessageProcessor) =
	authProcess<SendMessageRequest, IdResponse, MessageContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { baseResponseId() }
	)