package ru.medals.domain.reward.bussines.workers.nominee

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseId
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.nomineeUser(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryResponseId(repository = "reward", "Внутрення ошибка при номинации сотрудника") {
			rewardRepository.nomineeUser(
				nominee = nominee,
				sourceId = principalUser.id,
				departmentId = departmentId
			)
		}
	}
}