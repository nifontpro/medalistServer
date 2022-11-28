package ru.medals.domain.award.bussines.validate

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.validateAward(title: String) = worker {
	this.title = title
	on {
		state == ContextState.RUNNING && !isNew &&
				awardState == AwardState.AWARD && awardRelate?.state == AwardState.AWARD
	}
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "empty",
				description = "Сотрудник уже награжден этой премией"
			)
		)
	}
}