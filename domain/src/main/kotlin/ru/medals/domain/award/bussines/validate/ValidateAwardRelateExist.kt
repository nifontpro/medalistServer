package ru.medals.domain.award.bussines.validate

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.validateAwardRelateExist(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && isNew }
	handle {
		fail(
			errorValidation(
				field = "awardRelate",
				violationCode = "not exist",
				description = "Сотрудник не найден или не был удостоен этой награды"
			)
		)
	}
}

