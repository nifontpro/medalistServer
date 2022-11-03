package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.auth.util.checkAuthMinOwner
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка уровня - владелец компаний
 * В базовом контексте необходимы companyId, principalUser
 */
fun <T : BaseContext> ICorChainDsl<T>.validateOwnerLevel(
	title: String = "Проверка уровня доступа не ниже OWNER"
) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "owner level",
				violationCode = "internal",
				description = "Внутренний сбой при проверке уровня доступа"
			)
		)
	}
	handle {
		if (!checkAuthMinOwner(
				companyId = companyIdValid,
				principalUser = principalUser
			)
		) {
			fail(
				errorUnauthorized(
					role = "owner",
					description = "владелец компаний"
				)
			)
		}
	}
}