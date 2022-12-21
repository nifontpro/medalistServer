package ru.medals.data.message.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface MessageRepoErrors {

	companion object {
		private const val REPO = "message"

		fun errorMessageGet() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO get error",
				description = "Ошибка получения сообщений"
			)
		)

		fun errorMessageNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO not found",
				description = "Сообщение не найдено"
			)
		)

		fun errorMessageWrite() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO update error",
				description = "Ошибка записи сообщения"
			)
		)

		fun errorMessageDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO delete error",
				description = "Ошибка удаления сообщения"
			)
		)

	}
}