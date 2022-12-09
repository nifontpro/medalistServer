package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.trimFieldEmailAndCopyToValid(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		emailValid = email?.trim() ?: ""
	}
}
