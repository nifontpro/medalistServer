package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.core.bussines.helper.otherError
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.doesDepartmentContainUsers(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Внутренний сбой БД"
			)
		)
	}
	handle {
		if (userRepository.getUsersCountByDepartment(departmentIdValid) > 0) {
			fail(
				otherError(
					code = "not empty",
					field = "department",
					description = "Невозможно удалить отдел с сотрудниками"
				)
			)
		}
	}
}