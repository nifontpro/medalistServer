package ru.medals.domain.reward.bussines.validation

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.model.RewardState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.validateRewardActive(title: String) = worker {
	this.title = title
	on { reward.state == RewardState.ACTIVE && state == ContextState.RUNNING }
	handle {
		fail(
			errorValidation(
				field = "state",
				violationCode = "active",
				description = "Награда уже присвоена"
			)
		)
	}
}
