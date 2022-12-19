package ru.medals.domain.user.bussines.workers

import ru.medals.domain.auth.util.hashPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.createUser(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {

		user = user.copy(
			hashPassword = hashPassword(password),
			score = 0,
			currentScore = 0,
			awardCount = 0,
		)

		user = checkRepositoryData {
			userRepository.createUser(user = user)
		} ?: return@handle
	}
}