package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.deleteCompany(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		company = checkRepositoryData {
			companyRepository.deleteCompany(companyIdValid)
		} ?: return@handle
	}
}