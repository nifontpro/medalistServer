package ru.medals.domain.auth.bussines.validate

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.response.ApiResponseMessages
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.validateUserWithEmailExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		if (userRepository.getUserByEmail(email) != null)
			fail(
				errorValidation(
					field = "email",
					violationCode = "exist",
					description = ApiResponseMessages.USER_EMAIL_EXISTS
				)
			)
	}
}
