package ru.medals.domain.medal.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.validateMedalNameEmpty(title: String) = worker {
	this.title = title
	on { updateMedal.name.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "name",
				violationCode = "empty",
				description = "Не должно быть пустым"
			)
		)
	}
}
