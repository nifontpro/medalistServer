package ru.medals.domain.auth.bussines.workers

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.generateTokens(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		refreshToken = authService.refreshToken(user)
		accessToken = authService.accessToken(user)
	}
}
