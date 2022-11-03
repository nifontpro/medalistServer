package ru.medals.domain.medal.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MedalContext>.updateMedalImageS3(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryBool(repository = "medal", "Сбой при обновлении изображения медали") {
			medalRepository.updateImage(
				medalId = medalId,
				fileData = fileData
			)
		}
	}
}