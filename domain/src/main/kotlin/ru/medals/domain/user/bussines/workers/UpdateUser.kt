package ru.medals.domain.user.bussines.workers

import ru.medals.domain.auth.util.hashPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.updateUser(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		user = user.copy(
			hashPassword = user.hashPassword?.let { hashPassword(it) },
		)
		checkRepositoryBool(repository = "user", "Сбой при обновлении профиля сотрудника") {
			userRepository.updateProfile(user)
		}
	}
}