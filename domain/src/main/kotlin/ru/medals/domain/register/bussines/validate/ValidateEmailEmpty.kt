package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateEmailEmpty(title: String) = worker {
	this.title = title
	on { email.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "email",
				violationCode = "empty",
				description = "Email не должен быть пустым"
			)
		)
	}
}
