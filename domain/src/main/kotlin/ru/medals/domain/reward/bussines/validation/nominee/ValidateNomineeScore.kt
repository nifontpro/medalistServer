package ru.medals.domain.reward.bussines.validation.nominee

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.validateNomineeScore(title: String) = worker {
	this.title = title
	on { nominee.score !in 0..100 }
	handle {
		fail(
			errorValidation(
				field = "score",
				violationCode = "range",
				description = "Ценность награждения должна быть от 0 до 100%"
			)
		)
	}
}
