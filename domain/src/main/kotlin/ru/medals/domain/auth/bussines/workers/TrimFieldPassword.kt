package ru.medals.domain.auth.bussines.workers

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.trimFieldPassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		password = password.trim()
	}
}
