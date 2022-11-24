package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseId
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.createEmptyCompany(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryResponseId(repository = "company", "Внутрення ошибка при создании коммпании") {
			companyRepository.createEmptyCompany(principalUser.id)
		}
	}
}