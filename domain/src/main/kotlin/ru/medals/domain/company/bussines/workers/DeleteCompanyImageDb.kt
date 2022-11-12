package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkResponseData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.deleteCompanyImageDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkResponseData {
			companyRepository.deleteImage(
				companyId = companyIdValid,
				imageKey = imageKeyValid,
			)
		}
	}
}