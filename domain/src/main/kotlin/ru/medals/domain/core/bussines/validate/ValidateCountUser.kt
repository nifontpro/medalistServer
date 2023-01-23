package ru.medals.domain.core.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.validateUserCount(title: String) = worker {
	this.title = title
	on { count?.let { it < 0 } ?: false }
	handle {
		fail(
			errorValidation(
				field = "count",
				violationCode = "not valid",
				description = "Число сотрудников не может быть отрицательным"
			)
		)
	}
}
