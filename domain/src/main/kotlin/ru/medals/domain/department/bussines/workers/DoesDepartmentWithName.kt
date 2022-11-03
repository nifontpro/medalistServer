package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.bussines.helper.otherError
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.doesDepartmentWithNameExist(title: String) = worker {

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
		if (departmentRepository.doesOtherDepartmentWithName(
				name = departmentUpdate.name,
				companyId = department.companyId,
				departmentId = departmentUpdate.id
			)
		) {
			fail(
				otherError(
					code = "exist",
					field = "department",
					description = "В Вашей компании уже есть отдел с таким наименованием"
				)
			)
		}
	}
}