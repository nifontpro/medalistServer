package ru.medals.domain.medal.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkResponseData
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.deleteMedalImageDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkResponseData {
			medalRepository.deleteImage(
				medalId = medalId,
				imageKey = imageKeyValid,
			)
		}
	}
}