package ru.medals.domain.award.bussines.workers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseId
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.updateAwardImageS3(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryResponseId(
			repository = "award",
			description = "Сбой при обновлении изображения награды"
		) {
			awardRepository.updateImage(
				award = awardLite,
				fileData = fileData
			)
		}
	}
}