package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateTempRegRestorePswExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		if (registerRepository.checkTempRegExist(emailValid))
			fail(
				errorValidation(
					field = "password",
					violationCode = "restore",
					description = "Время для повторной отправки письма для сброса пароля не истекло"
				)
			)
	}
}
