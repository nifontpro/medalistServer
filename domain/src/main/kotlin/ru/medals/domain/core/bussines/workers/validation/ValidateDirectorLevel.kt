package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.auth.util.checkAuthMinDirector
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка уровня - директор и выше
 * В базовом контексте необходимы companyId, departmentId, principalUser
 */
fun <T : BaseContext> ICorChainDsl<T>.validateDirectorLevel(
	title: String = "Проверка уровня доступа не ниже DIRECTOR"
) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "director level",
				violationCode = "internal",
				description = "Внутренний сбой при проверке уровня доступа"
			)
		)
	}
	handle {
		if (!checkAuthMinDirector(
				companyId = companyId,
				departmentId = departmentId,
				principalUser = principalUser
			)
		) {
			fail(
				errorUnauthorized(
					role = "director",
					description = "Директор отдела"
				)
			)
		}
	}
}