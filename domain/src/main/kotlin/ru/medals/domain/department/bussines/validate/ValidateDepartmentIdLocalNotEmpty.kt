package ru.medals.domain.department.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.validateDepartmentIdLocalEmpty(title: String) = worker {
	this.title = title
	on { department.id.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "id",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
