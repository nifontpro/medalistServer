package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkResponseData
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.getAwardsByCompanyFromDb(title: String) =
	worker {

		this.title = title
		on { state == ContextState.RUNNING }

		handle {
			awards = checkResponseData {
				awardRepository.getAwardsByFilterMedal(
					companyId = companyIdValid,
					filter = searchFilter
				)
			} ?: return@handle
		}
	}