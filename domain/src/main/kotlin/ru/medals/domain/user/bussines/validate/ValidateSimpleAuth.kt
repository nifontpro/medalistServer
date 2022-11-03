package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateSimpleAuth(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && principalUser.getRolePriority() <= user.getRolePriority() }
	handle {
		fail(
			errorValidation(
				field = "role",
				violationCode = "unauthorized",
				description = "Не достаточно прав"
			)
		)
	}
}
