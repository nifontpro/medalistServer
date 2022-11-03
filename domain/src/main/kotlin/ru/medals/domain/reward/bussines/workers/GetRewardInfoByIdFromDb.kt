package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.model.MncSignature
import ru.medals.domain.reward.model.RewardInfo
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.getRewardInfoFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "reward",
				violationCode = "internal",
				description = "Сбой получения награды"
			)
		)
	}
	handle {
		reward = rewardRepository.getRewardById(rewardId) ?: run {
			fail(
				errorDb(
					repository = "reward",
					violationCode = "not found",
					description = "Награждение не найдено"
				)
			)
			return@handle
		}

		// mnc - члены номинационной комиссии
		val mnc = userRepository.getAllMnc(reward.companyId)
		var allSignatures = true // Признак, что все поставили подписи
		val mncSignatures = mutableListOf<MncSignature>()

		mnc.forEach {
			val sign = reward.signatures.find { signature -> signature.mncId == it.id } ?: kotlin.run {
				allSignatures = false
				null
			}
			mncSignatures.add(
				MncSignature(
					mncId = it.id,
					sign = sign != null,
					dateSign = sign?.date,
					name = it.name ?: "",
					patronymic = it.patronymic,
					lastname = it.lastname,
				)
			)
		}

		rewardInfo = RewardInfo(
			reward = reward,
			mncSignatures = mncSignatures,
			allSignatures = allSignatures
		)
	}
}