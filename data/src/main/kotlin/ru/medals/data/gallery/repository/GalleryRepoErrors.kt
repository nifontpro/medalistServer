package ru.medals.data.gallery.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface GalleryRepoErrors {
	companion object {

		private const val REPO = "gallery"

		fun errorGalleryCreate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "create",
				description = "Ошибка создания объекта галереи"
			)
		)

		fun errorGalleryDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO i/o delete error",
				description = "Ошибка удаления медали"
			)
		)

		fun errorGetGallery() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "get error",
				description = "Ошибка чтения галереи"
			)
		)

		fun errorGalleryNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO not found",
				description = "Медаль не найдена"
			)
		)

		fun errorGalleryUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных награды"
			)
		)
	}
}