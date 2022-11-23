package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.ContextError
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.getDepartmentCompanyIdValid(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "department",
				violationCode = "internal",
				description = "Сбой получения отдела"
			)
		)
	}
	handle {
		val departmentFind = departmentRepository.getDepartmentById(departmentIdValid) ?: run {
			fail(
				errorDb(
					repository = "department",
					violationCode = "not found",
					description = "Отдел не найден",
					level = ContextError.Levels.INFO
				)
			)
			return@handle
		}
		companyIdValid = departmentFind.companyId
	}
}