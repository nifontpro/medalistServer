package ru.medals.domain.core.bussines.workers

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.finishOperation(
	title: String = "Завершение операции"
) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		state = ContextState.FINISHING
	}
}
