package ru.medals.domain.message.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.message.bussines.context.MessageContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MessageContext>.sendMessage(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		message = checkRepositoryData {
			messageRepository.send(message)
		} ?: return@handle
	}
}