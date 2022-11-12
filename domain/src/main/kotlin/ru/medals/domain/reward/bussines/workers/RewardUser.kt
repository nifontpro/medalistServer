package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkResponseData
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.rewardUser(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "reward",
				violationCode = "internal",
				description = "Сбой при награждении сотрудника"
			)
		)
	}
	handle {
		checkResponseData {
			rewardRepository.rewardUser(rewardId = rewardId)
		}
	}
}