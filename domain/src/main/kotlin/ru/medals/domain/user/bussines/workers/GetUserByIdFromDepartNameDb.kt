package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.ContextError
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.getUserByIdDepartNameFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Сбой получения сотрудника"
			)
		)
	}
	handle {
		user = userRepository.getUserByIdWithDepartmentName(userIdValid) ?: run {
			fail(
				errorDb(
					repository = "user",
					violationCode = "not found",
					description = "Сотрудник не найден",
					level = ContextError.Levels.INFO
				)
			)
			return@handle
		}
	}
}