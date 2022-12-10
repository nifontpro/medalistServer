package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.trimFieldUser(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		user = user.copy(
			id = user.id.trim(),
			login = user.login?.trim(),
			hashPassword = user.hashPassword?.trim(),
			email = user.email?.trim(),
			name = user.name?.trim(),
		)
	}
}
