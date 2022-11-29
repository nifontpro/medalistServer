package ru.medals.domain.award.bussines.validate

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.validateAwardState(title: String) = worker {
	this.title = title
	on { awardState == AwardState.NONE }
	handle {
		fail(
			errorValidation(
				field = "awardState",
				violationCode = "none",
				description = "Указан неверный тип награждения"
			)
		)
	}
}
