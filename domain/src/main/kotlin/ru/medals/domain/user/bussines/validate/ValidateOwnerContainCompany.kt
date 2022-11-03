package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateOwnerContainCompany(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && user.role == OWNER }
	handle {
		if (companyRepository.getCompaniesByOwnerId(user.id).isNotEmpty())
			fail(
				errorValidation(
					field = "company",
					violationCode = "exist",
					description = "Невозможно удалить владельца с зарегистрированными компаниями"
				)
			)
	}
}
