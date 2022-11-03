package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateLimit(title: String) = worker {
	this.title = title
	on { limit < 1 }
	handle {
		fail(
			errorValidation(
				field = "limit",
				violationCode = "not positive",
				description = "Количество записей должно быть положительным"
			)
		)
	}
}
