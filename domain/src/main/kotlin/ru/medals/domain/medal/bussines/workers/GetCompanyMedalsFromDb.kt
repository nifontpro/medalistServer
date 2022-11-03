package ru.medals.domain.medal.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.getCompanyMedalsFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "medal",
				violationCode = "internal",
				description = "Внутрення ошибка при получении медалей"
			)
		)
	}
	handle {
		medals = medalRepository.getCompanyMedals(companyId = companyIdValid, filter = searchFilter)
	}
}