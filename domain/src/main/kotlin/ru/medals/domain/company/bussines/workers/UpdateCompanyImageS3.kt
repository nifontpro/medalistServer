package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.updateCompanyImageS3(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryBool(repository = "company", "Сбой при обновлении изображения компании") {
			companyRepository.updateImage(
				companyId = companyIdValid,
				fileData = fileData
			)
		}
	}
}