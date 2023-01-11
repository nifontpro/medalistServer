package ru.medals.domain.core.bussines.validate

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.chain

@Suppress("unused")
fun <T : BaseContext> ICorChainDsl<T>.validation(block: ICorChainDsl<T>.() -> Unit) = chain {
	title = "Валидация"
	on { state == ContextState.RUNNING }
	block()
}