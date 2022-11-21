package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.response.ApiResponseMessages
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateTempRegWithEmailExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		if (registerRepository.checkTempRegExist(user.email ?: ""))
			fail(
				errorValidation(
					field = "email",
					violationCode = "register",
					description = ApiResponseMessages.TEMP_REG_USER_EMAIL_EXISTS
				)
			)
	}
}
