package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<ru.medals.domain.user.bussines.context.UserContext>.addYouOwnerOrAdmin(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING && (principalUser.role == OWNER || principalUser.role == ADMIN) }
	handle {
		if (searchFilter == null) {
			users.add(principalUser)
		} else {
			searchFilter?.let { filter ->
				val x = principalUser.name?.contains(filter, ignoreCase = true) ?: false
				val y = principalUser.lastname?.contains(filter, ignoreCase = true) ?: false
				val z = principalUser.patronymic?.contains(filter, ignoreCase = true) ?: false
				if (x || y || z) {
					users.add(principalUser)
				}
			}
		}
	}
}
