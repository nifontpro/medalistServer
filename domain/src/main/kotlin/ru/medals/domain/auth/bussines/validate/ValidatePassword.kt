package ru.medals.domain.auth.bussines.validate

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.auth.util.checkValidPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.validatePassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		if (!checkValidPassword(
				enteredPassword = password,
				hashPassword = user.hashPassword ?: " "
			)
		) {
			fail(
				errorValidation(
					field = "password",
					violationCode = "not valid",
					description = "Неверный пароль"
				)
			)
		}
	}
}
