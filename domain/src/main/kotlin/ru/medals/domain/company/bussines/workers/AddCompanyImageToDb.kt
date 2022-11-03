package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.addCompanyImageToDb(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryResponseData {
			companyRepository.addImage(
				companyId = companyIdValid,
				fileData = fileData
			)
		}
	}
}