package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.getDepartmentIdsDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		ids = checkRepositoryData {
			departmentRepository.getIds()
		} ?: return@handle
	}
}