package ru.medals.data.award.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface AwardRepoErrors {
	companion object {

		private const val REPO = "award"

		fun errorAwardCreate() = RepositoryData.error(
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

		fun errorAwardDeleteContainsUser() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "not found",
				description = "Удаление награды невозможно, так как ею награждены сотрудники"
			)
		)

		fun errorAwardDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "delete error",
				description = "Ошибка удаления награды"
			)
		)

		fun errorAwardUserDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "delete award user error",
				description = "Ошибка удаления награждения сотрудника"
			)
		)

		fun errorAwardUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "update error",
				description = "Ошибка обновления награды"
			)
		)

		fun errorAwardUser() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "award error",
				description = "Ошибка награждения сотрудника"
			)
		)

		fun errorGetAward() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "I/O error",
				description = "Ошибка получения данных о награде"
			)
		)
	}
}