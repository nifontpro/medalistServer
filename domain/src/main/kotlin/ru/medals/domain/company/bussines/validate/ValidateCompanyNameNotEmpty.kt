package ru.medals.domain.company.bussines.validate

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.validateCompanyNameEmpty(title: String) = worker {
	this.title = title
	on { company.name.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
