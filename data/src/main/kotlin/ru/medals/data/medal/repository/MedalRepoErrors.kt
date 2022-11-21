package ru.medals.data.medal.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface MedalRepoErrors {
	companion object {

		private const val REPO = "medal"

		fun errorMedalDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO i/o delete error",
				description = "Ошибка удаления медали"
			)
		)

		fun errorMedalNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO not found",
				description = "Медаль не найдена"
			)
		)

		fun errorMedalUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных награды"
			)
		)
	}
}