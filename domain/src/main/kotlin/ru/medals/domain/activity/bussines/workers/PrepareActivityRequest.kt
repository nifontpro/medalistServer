package ru.medals.domain.activity.bussines.workers

import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<ActivityContext>.prepareActivityRequest(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		activityQuery = activityQuery.copy(
			companyId = companyIdValid,
			filter = searchFilter
		)
	}

}