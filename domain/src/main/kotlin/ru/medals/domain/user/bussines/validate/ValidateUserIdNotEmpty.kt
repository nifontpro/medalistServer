package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateUserIdEmptyLocal(title: String) = worker {
	this.title = title
	on { user.id.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "userId",
				violationCode = "empty",
				description = "userId не должно быть пустым"
			)
		)
	}
}
