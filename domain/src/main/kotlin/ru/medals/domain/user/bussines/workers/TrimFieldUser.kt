package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.trimFieldUser(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		user = user.copy(
			login = user.login?.trim(),
			hashPassword = user.hashPassword?.trim(),
			email = user.email?.trim(),
			name = user.name?.trim(),
			patronymic = user.patronymic?.trim(),
			lastname = user.lastname?.trim(),
			bio = user.bio?.trim(),
		)
	}
}
