package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateUserPasswordEmpty(title: String) = worker {
	this.title = title
	on { user.hashPassword.isNullOrBlank() }
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
