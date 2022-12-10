package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateUserWithIdNotExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		if (!userRepository.verifyUserByIdExist(user.id))
			fail(
				errorValidation(
					field = "user",
					violationCode = "not exist",
					description = "Сотрудник не найден"
				)
			)
	}
}
