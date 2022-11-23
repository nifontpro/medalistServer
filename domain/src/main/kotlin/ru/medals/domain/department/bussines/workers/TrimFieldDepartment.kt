package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.trimFieldDepartment(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		department = department.copy(
			name = department.name.trim(),
			description = department.description?.trim(),
		)
		departmentIdValid = department.id // Для получения отдела в следующей операции
	}
}
