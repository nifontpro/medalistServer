package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.ContextError
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.getCompanyById(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "company",
				violationCode = "internal",
				description = "Внутрення ошибка при чтении компании"
			)
		)
	}
	handle {
		company = companyRepository.getCompanyById(companyIdValid) ?: run {
			fail(
				errorDb(
					repository = "company",
					violationCode = "not found",
					description = "Компания не найдена",
					level = ContextError.Levels.INFO
				)
			)
			return@handle
		}
	}
}