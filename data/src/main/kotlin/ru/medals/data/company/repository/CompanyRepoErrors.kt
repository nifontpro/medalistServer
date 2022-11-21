package ru.medals.data.company.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface CompanyRepoErrors {
	companion object {

		private const val REPO = "company"

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
	}
}