package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.doesUserMnc(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Сбой получения списка ЧНК"
			)
		)
	}
	handle {
		val mncIds = userRepository.getAllMncIds(reward.companyId)
		if (!mncIds.contains(principalUser.id)) {
			errorValidation(
				field = "mnc",
				violationCode = "unauthorized",
				description = "Вы не имеете права подписи"
			)
		}
	}
}