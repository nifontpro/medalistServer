package ru.medals.domain.core.bussines.workers

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.initStatus(title: String) = worker {
	this.title = title
	on { state == ContextState.NONE }
	handle { state = ContextState.STARTING }
}
