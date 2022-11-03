package ru.medals.domain.user.bussines.workers

import ru.medals.domain.auth.util.checkAuthMinAdmin
import ru.medals.domain.auth.util.checkAuthMinDirector
import ru.medals.domain.auth.util.checkAuthMinOwner
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorUnauthorized
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.extendedAuth(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		companyId = user.companyId
		departmentId = user.departmentId

		val isAuth = when (user.role) {
			User.ADMIN -> {
				checkAuthMinOwner(
					companyId = companyId,
					principalUser = principalUser
				)
			}

			User.DIRECTOR -> {
				checkAuthMinAdmin(
					companyId = companyId,
					principalUser = principalUser
				)
			}

			User.USER -> {
				checkAuthMinDirector(
					companyId = companyId,
					departmentId = departmentId,
					principalUser = principalUser
				)
			}

			else -> {
				false
			}
		}

		if (!isAuth) {
			fail(
				errorUnauthorized(
					role = principalUser.role ?: "неизвестная",
					description = "Недостаточно прав для создания сотрудника"
				)
			)
		}
	}
}
