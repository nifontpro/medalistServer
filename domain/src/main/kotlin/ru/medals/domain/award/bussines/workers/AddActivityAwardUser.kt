package ru.medals.domain.award.bussines.workers

import ru.medals.domain.activity.model.Activity
import ru.medals.domain.activity.model.ActivityState
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.addActivityAwardUser(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		val activity = Activity(
			userId = userIdValid,
			awardId = award.id,
			companyId = award.companyId,
			state = if (awardState == AwardState.AWARD) ActivityState.AWARD_USER else ActivityState.NOMINEE_USER,
			date = System.currentTimeMillis()
		)
		checkRepositoryData {
			activityRepository.insert(activity = activity)
		}
	}
}