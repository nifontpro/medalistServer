package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateUserLoginEmpty(title: String) = worker {
	this.title = title
	on { user.login.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "login",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
