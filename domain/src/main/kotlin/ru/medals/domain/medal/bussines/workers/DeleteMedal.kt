package ru.medals.domain.medal.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.deleteMedal(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		medal = checkRepositoryData {
			medalRepository.deleteMedal(medal = medal)
		} ?: return@handle
	}
}