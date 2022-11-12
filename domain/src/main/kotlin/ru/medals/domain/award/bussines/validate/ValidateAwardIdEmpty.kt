package ru.medals.domain.award.bussines.validate

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.validateAwardNameEmpty(title: String) = worker {
	this.title = title
	on { award.name.isNullOrBlank() }
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "empty",
				description = "Название не должно быть пустым"
			)
		)
	}
}