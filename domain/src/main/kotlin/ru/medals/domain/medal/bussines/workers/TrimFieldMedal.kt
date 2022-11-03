package ru.medals.domain.medal.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.trimFieldMedal(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		updateMedal = updateMedal.copy(
			name = updateMedal.name.trim(),
			description = updateMedal.description?.trim(),
			companyId = medal.companyId
		)
	}
}
