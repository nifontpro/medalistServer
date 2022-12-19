package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.util.Constants.TEST_PASSWORD
import ru.medals.domain.core.util.getRandomPassword
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.generatePassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		password = if (prodMode()) {
			getRandomPassword()
		} else {
			TEST_PASSWORD
		}
	}
}
