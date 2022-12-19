package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateNewPasswordEmpty(title: String) = worker {
	this.title = title
	on { newPassword.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "password",
				violationCode = "empty",
				description = "Новый пароль не должен быть пустым"
			)
		)
	}
}
