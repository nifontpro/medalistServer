package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.response.ApiResponseMessages
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateUserWithLoginExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		user.login?.let {
			if (userRepository.getUserByLogin(it) != null)
				fail(
					errorValidation(
						field = "login",
						violationCode = "exist",
						description = ApiResponseMessages.USER_LOGIN_EXISTS
					)
				)
		}
	}
}
