package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.auth.util.checkAuthMinAdmin
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка уровня - администратор и выше
 * В базовом контексте необходимы companyIdValid, principalUser
 */
fun <T : BaseContext> ICorChainDsl<T>.validateAdminLevel(
	title: String = "Проверка уровня доступа не ниже ADMIN"
) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "admin level",
				violationCode = "internal",
				description = "Внутренний сбой при проверке уровня доступа"
			)
		)
	}
	handle {
		if (!checkAuthMinAdmin(
				companyId = companyIdValid,
				principalUser = principalUser
			)
		) {
			fail(
				errorUnauthorized(
					role = "admin",
					description = "Администратор компании"
				)
			)
		}
	}
}