package ru.medals.domain.medal.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseId
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.createMedal(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryResponseId(repository = "medal", "Внутрення ошибка при создании медали") {
			medalRepository.createEmptyMedal(companyId = companyIdValid, isSystem = updateMedal.isSystem)
		}
	}
}