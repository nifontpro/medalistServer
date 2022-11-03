package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.bussines.utils.checkRewardSignature
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.checkAllSignatures(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Внутренняя ошибка чтения ЧНК"
			)
		)
	}
	handle {
		val mncIds = userRepository.getAllMncIds(reward.companyId)
		val signaturesIds = reward.signatures.map { it.mncId }
		if (!checkRewardSignature(mncIds = mncIds, rewardIds = signaturesIds)) {
			fail(
				errorValidation(
					field = "signatures",
					violationCode = "not sign",
					description = "Не все члены номинационой комиссии поставили подписи"
				)
			)
			return@handle
		}
	}
}