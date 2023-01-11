package ru.medals.domain.core.bussines.validate

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateUserExist(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryData { userRepository.checkExist(userIdValid) }
	}

}