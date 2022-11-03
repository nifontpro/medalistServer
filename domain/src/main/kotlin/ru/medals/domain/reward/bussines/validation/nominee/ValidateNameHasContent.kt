package ru.medals.domain.reward.bussines.validation.nominee

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.validateNameHasContent(title: String) = worker {
	this.title = title
	val regExp = Regex("\\p{L}")
	on { nominee.name.isNotEmpty() && !nominee.name.contains(regExp) }
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "noContent",
				description = "Поле должно содержать буквы"
			)
		)
	}
}
