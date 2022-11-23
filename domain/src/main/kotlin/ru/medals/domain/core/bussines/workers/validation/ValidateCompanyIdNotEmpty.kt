package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateCompanyIdEmpty(title: String) = worker {
	this.title = title
	on { companyId.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "companyId",
				violationCode = "empty",
				description = "Поле companyId не должно быть пустым"
			)
		)
	}
}
