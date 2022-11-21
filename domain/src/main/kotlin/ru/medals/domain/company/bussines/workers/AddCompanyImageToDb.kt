package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.addCompanyImageToDb(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryData {
			companyRepository.addImage(
				companyId = companyIdValid,
				fileData = fileData
			)
		}
	}
}