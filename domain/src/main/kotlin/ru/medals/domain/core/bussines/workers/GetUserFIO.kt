package ru.medals.domain.core.bussines.workers

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.getUserFIO(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		userFIO = checkRepositoryData {
			userRepository.getFIO(userId = userIdValid)
		} ?: return@handle
	}

}