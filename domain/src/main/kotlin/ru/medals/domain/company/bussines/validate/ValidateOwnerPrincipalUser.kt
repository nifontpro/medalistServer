package ru.medals.domain.company.bussines.validate

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.model.User.Companion.OWNER_PRIORITY
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.validateOwnerPrincipalUser(title: String) = worker {
	this.title = title
	on { principalUser.getRolePriority() < OWNER_PRIORITY }
	handle {
		fail(
			errorValidation(
				field = "role",
				violationCode = "unauthorized",
				description = "Создавать компании может только владелец"
			)
		)
	}
}
