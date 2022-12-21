package ru.medals.domain.message.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.message.bussines.context.MessageContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MessageContext>.getMessageByUserDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		messages = checkRepositoryData {
			messageRepository.getByUser(userId = userIdValid)
		} ?: return@handle
	}
}