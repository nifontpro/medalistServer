package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.checkUserExist(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryData { userRepository.checkExist(userIdValid) }
	}

}