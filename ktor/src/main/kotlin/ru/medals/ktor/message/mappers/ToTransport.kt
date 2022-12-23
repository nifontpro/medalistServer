package ru.medals.ktor.message.mappers

import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.model.Message

fun MessageContext.toTransportGetMessage(): Message = message

fun MessageContext.toTransportGetMessages(): List<Message> = messages