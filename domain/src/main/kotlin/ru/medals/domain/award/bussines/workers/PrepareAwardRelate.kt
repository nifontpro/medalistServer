package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.prepareAwardRelate(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		awardRelateValid = AwardRelate(
			userId = userIdValid,
			state = awardState,
			nomineeDate = if (awardState == AwardState.NOMINEE) System.currentTimeMillis() else awardRelate?.nomineeDate,
			awardDate = if (awardState == AwardState.AWARD) System.currentTimeMillis() else null,
			nomineeUserId = if (awardState == AwardState.NOMINEE) principalUser.id else awardRelate?.nomineeUserId,
			awardUserId = if (awardState == AwardState.AWARD) principalUser.id else null,
		)
	}
}