package ru.medals.data.activity.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface ActivityRepoErrors {
	companion object {

		private const val REPO = "activity"

		fun errorActivityCreate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "create error",
				description = "Ошибка создания активности"
			)
		)

		/*fun errorAwardNotFound() = RepositoryData.error(
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

		fun errorAwardImageDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "image delete error",
				description = "Ошибка удаления изображения у награды"
			)
		)

		fun errorAwardImageNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "image not found",
				description = "У награды отсутствует изображение"
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

		fun errorGetAwardCount() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "I/O error",
				description = "Ошибка получения данных о количестве наград"
			)
		)*/
	}
}