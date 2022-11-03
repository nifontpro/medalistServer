package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.addAdminsIfYouOwner(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING && principalUser.role == OWNER && companyId != null }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Сбой получения администраторов компаний владельца"
			)
		)
	}
	handle {
		companyId?.let { users.addAll(userRepository.getCompanyAdmins(companyId = it, filter = searchFilter)) }
	}
}