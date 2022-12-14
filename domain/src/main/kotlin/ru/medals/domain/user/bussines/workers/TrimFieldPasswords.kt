package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.trimFieldPasswords(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		password = password.trim()
		newPassword = newPassword.trim()
	}
}
