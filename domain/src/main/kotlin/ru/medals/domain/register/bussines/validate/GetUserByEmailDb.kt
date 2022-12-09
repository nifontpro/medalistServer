package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.getUserByEmailDb(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		user = userRepository.getUserByEmail(email = emailValid) ?: run {
			fail(
				errorValidation(
					field = "email",
					violationCode = "exist",
					description = "Сотрудник с таким email не найден!"
				)
			)
			return@handle
		}

	}
}
