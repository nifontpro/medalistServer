package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.trimFieldAward(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		award = award.copy(
			name = award.name?.trim(),
			description = award.description?.trim(),
			companyId = companyIdValid,
			medalId = award.medalId?.trim(),
			criteria = award.criteria?.trim()
		)
	}
}
