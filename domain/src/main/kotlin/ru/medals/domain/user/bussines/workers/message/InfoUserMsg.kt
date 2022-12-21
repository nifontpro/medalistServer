@file:Suppress("unused")

package ru.medals.domain.user.bussines.workers.message

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.model.MessageType
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.infoUserMsg(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		val message = Message(
			fromId = "get by id",
			toId = userIdValid,
			type = MessageType.USER,
			theme = "Тестовое сообщение",
			themeSlug = "test",
			text = "Привет! это тестовое сообщение!"
		)

		messageRepository.send(message)
	}
}
