package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.getUsersByCompanyWithAwardsFullDb(title: String) =
	worker {
		this.title = title
		on { state == ContextState.RUNNING }
		handle {
			usersAwardsUnion = checkRepositoryData {
				userRepository.getUsersByCompanyWithAwardsUnion(companyId = companyIdValid, filter = searchFilter)
			} ?: return@handle
		}
	}