package ru.medals.domain.user.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.model.User.Companion.USER
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateDepartmentIdUser(title: String) = worker {
	this.title = title
	on { (user.role == DIRECTOR || user.role == USER) && departmentId.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "departmentId",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
