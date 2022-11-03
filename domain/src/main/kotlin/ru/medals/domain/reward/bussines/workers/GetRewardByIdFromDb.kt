package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.ContextError
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.getRewardByIdFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "reward",
				violationCode = "internal",
				description = "Сбой получения награды"
			)
		)
	}
	handle {
		reward = rewardRepository.getRewardById(rewardId) ?: run {
			fail(
				errorDb(
					repository = "reward",
					violationCode = "not found",
					description = "Награждение не найдено",
					level = ContextError.Levels.INFO
				)
			)
			return@handle
		}
	}
}