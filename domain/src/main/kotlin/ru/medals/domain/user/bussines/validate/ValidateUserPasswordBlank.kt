package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка пароля на пустые символы с допущением null (в этом случае пароль не меняется)
 */
fun ICorChainDsl<ru.medals.domain.user.bussines.context.UserContext>.validateUserPasswordBlank(title: String) = worker {
	this.title = title
	on { user.hashPassword?.isBlank() ?: false }
	handle {
		fail(
			errorValidation(
				field = "password",
				violationCode = "empty",
				description = "Пароль не должен быть пустым"
			)
		)
	}
}
