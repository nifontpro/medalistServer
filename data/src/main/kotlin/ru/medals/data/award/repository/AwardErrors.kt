package ru.medals.data.award.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface AwardErrors {
	companion object {

		private const val REPO = "award"

		fun errorCreate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "create error",
				description = "Ошибка создания награды"
			)
		)

		fun errorAwardNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "not found",
				description = "Награждение не найдено"
			)
		)

		fun errorDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "delete error",
				description = "Ошибка удаления награды"
			)
		)

		fun errorUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "update error",
				description = "Ошибка обновления награды"
			)
		)

		fun errorIO() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "I/O error",
				description = "Ошибка обмена данными"
			)
		)

		fun errorMedalNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "Medal not found",
				description = "Нет медали с заданным id"
			)
		)

	}
}