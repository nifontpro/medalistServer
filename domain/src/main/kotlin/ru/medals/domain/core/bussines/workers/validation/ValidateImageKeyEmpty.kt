package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateImageKeyEmpty(title: String) = worker {
	this.title = title
	on { imageKey.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "imageKey",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
