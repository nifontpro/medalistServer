package ru.medals.domain.reward.bussines.workers.info

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.getUserRewardsDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "reward",
				violationCode = "internal",
				description = "Сбой получения наград сотрудника"
			)
		)
	}
	handle {
		rewards = rewardRepository.getRewardsByUser(userIdValid)
	}
}