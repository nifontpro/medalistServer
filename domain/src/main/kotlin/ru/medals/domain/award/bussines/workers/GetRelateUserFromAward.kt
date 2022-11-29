package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.getRelateUserFromAward(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		awardRelate = award.relations.find { it.userId == userIdValid }
		companyIdValid = award.companyId
		isNew = awardRelate == null
	}

}