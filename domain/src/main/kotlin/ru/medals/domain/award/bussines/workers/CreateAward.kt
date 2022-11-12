package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkResponseData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.createAward(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkResponseData {
			awardRepository.create(award = award)
		}?.let { newAward -> award = newAward }
	}
}