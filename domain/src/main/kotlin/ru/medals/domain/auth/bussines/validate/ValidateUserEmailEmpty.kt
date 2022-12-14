package ru.medals.domain.auth.bussines.validate

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.validateUserEmailEmpty(title: String) = worker {
	this.title = title
	on { email.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "email",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
