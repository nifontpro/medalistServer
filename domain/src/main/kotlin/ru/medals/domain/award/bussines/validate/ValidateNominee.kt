package ru.medals.domain.award.bussines.validate

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.validateNominee(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && !isNew && awardState == AwardState.NOMINEE }
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "empty",
				description = "Сотрудник уже номинирован или награжден на премию"
			)
		)
	}
}