package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.prepareAuth(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		companyId = reward.companyId
		departmentId = reward.departmentId
	}
}