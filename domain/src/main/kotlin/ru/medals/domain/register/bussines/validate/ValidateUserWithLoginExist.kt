package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.response.ApiResponseMessages
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateUserWithLoginExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		if (userRepository.verifyUserByLoginExist(user.login ?: ""))
			fail(
				errorValidation(
					field = "login",
					violationCode = "exist",
					description = ApiResponseMessages.USER_LOGIN_EXISTS
				)
			)
	}
}
