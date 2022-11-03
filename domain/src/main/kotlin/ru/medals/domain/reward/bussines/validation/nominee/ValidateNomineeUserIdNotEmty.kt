package ru.medals.domain.reward.bussines.validation.nominee

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.validateNomineeUserIdNotEmpty(title: String) = worker {
	this.title = title
	on { nominee.userId.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "userId",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
