package ru.medals.domain.medal.bussines.validate

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.validateSystemMedal(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && (medal.isSystem || medal.companyId.isNullOrBlank()) }
	handle {
		fail(
			errorValidation(
				field = "medal",
				violationCode = "system",
				description = "Обновление системной медали невозможно"
			)
		)
	}
}
