package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.getMedalByIdFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "medal",
				violationCode = "internal",
				description = "Сбой получения медали"
			)
		)
	}
	handle {
		val medal = medalRepository.getMedalById(nominee.medalId) ?: run {
			fail(
				errorDb(
					repository = "medal",
					violationCode = "not found",
					description = "Медаль не найдена"
				)
			)
			return@handle
		}

		if (!medal.isSystem && medal.companyId != nominee.companyId) {
			fail(
				errorValidation(
					field = "companyId",
					violationCode = "other company",
					description = "Медаль из другой компании"
				)
			)
			return@handle
		}
	}
}