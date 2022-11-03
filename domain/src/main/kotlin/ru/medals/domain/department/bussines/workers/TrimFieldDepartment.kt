package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.trimFieldDepartment(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		departmentUpdate = departmentUpdate.copy(
			name = departmentUpdate.name.trim(),
			description = departmentUpdate.description?.trim(),
		)
		departmentIdValid = departmentUpdate.id // Для получения отдела в следующей операции
	}
}
