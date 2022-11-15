package ru.medals.data.appoint.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface AppointErrors {
	companion object {

		private const val REPO = "appoint"

		fun errorAppointCreate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "i/o error",
				description = "Ошибка создания ассоциации"
			)
		)

		fun errorAppointDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "i/o error",
				description = "Ошибка удаления ассоциации"
			)
		)

		fun errorAppointGet() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "i/o error",
				description = "Ошибка чтения ассоциации"
			)
		)

		fun errorAppointNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "not found",
				description = "Ассоциация не найдена"
			)
		)

		fun errorUserNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "not found",
				description = "Сотрудник не найден"
			)
		)

		fun errorDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "delete error",
				description = "Ошибка удаления связи"
			)
		)

		fun errorUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "update error",
				description = "Ошибка обновления награды"
			)
		)
	}
}