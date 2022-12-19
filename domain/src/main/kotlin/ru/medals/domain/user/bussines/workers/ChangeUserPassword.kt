package ru.medals.domain.user.bussines.workers

import ru.medals.domain.auth.util.hashPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.updateUserPassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryBool(
			repository = "user",
			description = "Ошибка обновления пароля сотрудника"
		) {
			userRepository.updateHashPassword(
				userId = userIdValid,
				hashPassword = hashPassword(newPassword)
			)
		}
	}
}
