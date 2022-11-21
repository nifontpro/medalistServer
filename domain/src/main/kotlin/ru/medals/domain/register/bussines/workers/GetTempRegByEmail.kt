package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.getTempRegByEmail(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		tempReg = registerRepository.getTempRegByEmail(user.email ?: "") ?: kotlin.run {
			fail(
				errorValidation(
					field = "register",
					violationCode = "not valid",
					description = "Время регистрации истекло или неверный адрес почты"
				)
			)
			return@handle
		}
	}
}
