package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseId
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.createDepartment(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {

		checkRepositoryResponseId(repository = "department", "Внутрення ошибка при создании отдела") {
			departmentRepository.createDepartment(companyIdValid)
		}
	}
}