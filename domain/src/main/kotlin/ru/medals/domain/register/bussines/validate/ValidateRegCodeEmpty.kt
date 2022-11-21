package ru.medals.domain.register.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.validateRegCodeEmpty(title: String) = worker {
	this.title = title
	on { code.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "code",
				violationCode = "empty",
				description = "Код не должен быть пустым"
			)
		)
	}
}
