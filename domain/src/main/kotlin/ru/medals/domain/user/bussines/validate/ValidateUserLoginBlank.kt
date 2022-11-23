package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateUserLoginBlank(title: String) = worker {
	this.title = title
	on { user.login?.isBlank() ?: false }
	handle {
		fail(
			errorValidation(
				field = "login",
				violationCode = "empty",
				description = "Login не должен быть пустым"
			)
		)
	}
}
