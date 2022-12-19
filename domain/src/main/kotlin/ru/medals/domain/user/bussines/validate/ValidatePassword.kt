package ru.medals.domain.user.bussines.validate

import ru.medals.domain.auth.util.checkValidPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

@Suppress("DuplicatedCode")
fun ICorChainDsl<UserContext>.validatePassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && prodMode()}
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
