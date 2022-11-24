package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.bussines.helper.otherError
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.doesOtherCompanyWithNameExist(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "company",
				violationCode = "internal",
				description = "Внутренний сбой БД"
			)
		)
	}
	handle {
		if (companyRepository.doesOtherCompanyByOwnerWithName(
				name = company.name,
				companyId = company.id,
				ownerId = principalUser.id
			)
		) {
			fail(
				otherError(
					code = "exist",
					field = "company",
					description = "У Вас уже есть компания с таким наименованием"
				)
			)
		}
	}
}