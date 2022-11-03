package ru.medals.domain.core.bussines.workers.validation

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка уровня - директор и выше
 * В базовом контексте необходимы companyId, departmentId, principalUser
 */
fun <T : BaseContext> ICorChainDsl<T>.validateFailAuth(
	title: String
) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		fail(
			errorUnauthorized(
				role = "invalid",
				description = "недопустимый уровень"
			)
		)
	}
}