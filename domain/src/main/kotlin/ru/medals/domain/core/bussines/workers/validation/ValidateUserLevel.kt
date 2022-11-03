package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.auth.util.checkAuthMinUser
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка уровня - сотрудник и выше
 * В базовом контексте необходимы userIdValid, principalUser
 */
fun <T : BaseContext> ICorChainDsl<T>.validateUserLevel(
	title: String = "Проверка уровня доступа не ниже USER"
) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user level",
				violationCode = "internal",
				description = "Внутренний сбой при проверке уровня доступа"
			)
		)
	}
	handle {
		if (!checkAuthMinUser(
				userId = userIdValid,
				principalUser = principalUser
			)
		) {
			fail(
				errorUnauthorized(
					role = "director",
					description = "директор отдела",
				)
			)
		}
	}
}