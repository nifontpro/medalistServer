package ru.medals.domain.auth.bussines.workers

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.getUserByLoginFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "auth",
				violationCode = "internal",
				description = "Ошибка входа"
			)
		)
	}
	handle {
		user = userRepository.getUserByLogin(login) ?: run {
			fail(
				errorValidation(
					field = "login/password",
					violationCode = "not valid",
					description = "Неверный логин/пароль"
				)
			)
			return@handle
		}
	}
}