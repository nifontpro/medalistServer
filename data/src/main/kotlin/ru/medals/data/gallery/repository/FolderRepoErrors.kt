package ru.medals.data.gallery.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface FolderRepoErrors {
	companion object {

		private const val REPO = "folder"

		fun errorFolderCreate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "create",
				description = "Ошибка создания папки"
			)
		)

		fun errorFolderCreateByParent() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "create",
				description = "Ошибка создания папки (добавление в родителькую папку)"
			)
		)

		fun errorFolderDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO db delete error",
				description = "Ошибка удаления объекта галереи"
			)
		)

		fun errorGetFolder() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "get error",
				description = "Ошибка чтения папок"
			)
		)

		fun errorFolderNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO not found",
				description = "Объект галереи не найден"
			)
		)

		fun errorFolderUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления объекта галереи"
			)
		)
	}
}