package ru.medals.domain.company.bussines.workers

import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.bussines.helper.otherError
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<CompanyContext>.doesCompanyContainsDepartments(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "department",
				violationCode = "internal",
				description = "Внутренний сбой БД"
			)
		)
	}
	handle {
		if (departmentRepository.getDepartmentsCount(companyIdValid) > 0) {
			fail(
				otherError(
					code = "not empty",
					field = "company",
					description = "Невозможно удалить компанию с отделами"
				)
			)
		}
	}
}