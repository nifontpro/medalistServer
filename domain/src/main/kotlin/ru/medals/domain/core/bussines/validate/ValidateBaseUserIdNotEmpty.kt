package ru.medals.domain.core.bussines.validate

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateUserIdEmpty(title: String) = worker {
	this.title = title
	on { userId.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "userId",
				violationCode = "empty",
				description = "userId не должно быть пустым"
			)
		)
	}
}
