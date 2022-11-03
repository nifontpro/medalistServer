package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.chain

fun <T : BaseContext> ICorChainDsl<T>.validation(block: ICorChainDsl<T>.() -> Unit) = chain {
	title = "Валидация"
	on { state == ContextState.RUNNING }
	block()
}