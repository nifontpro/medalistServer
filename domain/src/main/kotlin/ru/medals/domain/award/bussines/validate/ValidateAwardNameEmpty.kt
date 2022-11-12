package ru.medals.domain.award.bussines.validate

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.validateAwardIdEmpty(title: String) = worker {
	this.title = title
	on { awardId.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "awardId",
				violationCode = "empty",
				description = "id не должен быть пустым"
			)
		)
	}
}

