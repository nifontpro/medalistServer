package ru.medals.ktor.message.mappers

import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.model.Message

fun MessageContext.toTransportGetMessages(): List<Message> = messages