package ru.medals.domain.core.bussines.validate

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

/**
 * Проверка уровня - только данный сотрудник
 * В базовом контексте необходимы userIdValid, principalUser
 */
@Suppress("unused")
fun <T : BaseContext> ICorChainDsl<T>.validateThisUserLevel(
	title: String = "Проверка уровня доступа только этот сотрудник"
) = worker {

	this.title = title
	on { state == ContextState.RUNNING && userIdValid != principalUser.id}

	handle {
		fail(
			errorUnauthorized(
				role = "director",
				description = "Только данный сотрудник",
			)
		)
	}
}