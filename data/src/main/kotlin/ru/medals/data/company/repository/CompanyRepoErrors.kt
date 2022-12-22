package ru.medals.data.company.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface CompanyRepoErrors {
	companion object {

		private const val REPO = "company"

		fun errorCompanyCreate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO i/o create error",
				description = "Ошибка создания компании"
			)
		)

		fun errorCompanyDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO i/o delete error",
				description = "Ошибка удаления компании"
			)
		)

		fun errorCompanyNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO not found",
				description = "Компания не найдена"
			)
		)

		fun errorCompanyUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных организации"
			)
		)

		fun errorCompanyImageDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "image delete error",
				description = "Ошибка удаления изображения у компании"
			)
		)

		fun errorCompanyImageNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "image not found",
				description = "У компании отсутствует изображение"
			)
		)

	}
}