package ru.medals.domain.register.bussines.workers

import ru.medals.domain.auth.util.hashPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.resetUserPassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryBool(
			repository = "user",
			description = "Ошибка сброса пароля"
		) {
			userRepository.updateHashPassword(
				userId = user.id,
				hashPassword = hashPassword(user.hashPassword ?: " ")
			)
		}
	}
}
