package ru.medals.domain.core.bussines.workers

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.IBaseCommand
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.chain
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.operation(
	title: String,
	command: IBaseCommand,
	block: ICorChainDsl<T>.() -> Unit
) = chain {
	on { this.command == command && state == ContextState.STARTING }
	this.title = title
	worker("Статус - операция выполняется") { state = ContextState.RUNNING }
	block()
}