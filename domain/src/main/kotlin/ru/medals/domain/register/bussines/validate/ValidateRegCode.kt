package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateRegCode(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && tempReg.code != code }
	handle {
		fail(
			errorValidation(
				field = "code",
				violationCode = "not valid",
				description = "Неверный код подтверждения"
			)
		)
	}
}
