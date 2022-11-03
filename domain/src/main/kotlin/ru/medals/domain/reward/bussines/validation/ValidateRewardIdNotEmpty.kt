package ru.medals.domain.reward.bussines.validation

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.validateRewardId(title: String) = worker {
	this.title = title
	on { rewardId.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "rewardId",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
