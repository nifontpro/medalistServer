package ru.medals.domain.medal.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.validateMedalScore(title: String) = worker {
	this.title = title
	on { updateMedal.score !in 0..100 }
	handle {
		fail(
			errorValidation(
				field = "score",
				violationCode = "range",
				description = "Должно быть от 0 до 100"
			)
		)
	}
}
