package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.trimFieldCompany(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		company = company.copy(
			name = company.name.trim(),
			description = company.description?.trim(),
			phone = company.phone?.trim(),
			email = company.email?.trim(),
			address = company.address?.trim(),
		)
	}
}
