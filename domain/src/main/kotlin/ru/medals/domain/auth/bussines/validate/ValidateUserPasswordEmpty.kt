package ru.medals.domain.auth.bussines.validate

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.validateUserPasswordEmpty(title: String) = worker {
	this.title = title
	on { password.isBlank() }
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
