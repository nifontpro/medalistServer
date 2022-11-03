package ru.medals.domain.reward.bussines.workers.nominee

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.trimFieldNomineeRequest(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		nominee.name = nominee.name.trim()
		nominee.description?.let { nominee.description = it.trim() }
		nominee.userId = nominee.userId.trim()
		nominee.medalId = nominee.medalId.trim()
		nominee.companyId = nominee.companyId.trim()
	}
}
