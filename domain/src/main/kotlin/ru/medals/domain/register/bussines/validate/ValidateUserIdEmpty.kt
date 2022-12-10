package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateUserIdEmpty(title: String) = worker {
	this.title = title
	on { user.id.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "id",
				violationCode = "empty",
				description = "user id не должен быть пустым"
			)
		)
	}
}
