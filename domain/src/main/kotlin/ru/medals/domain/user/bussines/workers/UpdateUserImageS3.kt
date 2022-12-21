package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.updateUserMainImageS3(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryBool(repository = "user", "Сбой при обновлении изображения сотрудника") {
			userRepository.updateImage(
				userId = userIdValid,
				fileData = fileData
			)
		}
	}
}