package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.deleteDepartment(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		department = checkRepositoryData {
			departmentRepository.deleteDepartment(department)
		} ?: return@handle
	}
}