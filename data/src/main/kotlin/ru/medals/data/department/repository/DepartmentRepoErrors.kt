package ru.medals.data.department.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface CompanyRepoErrors {
	companion object {

		private const val REPO = "department"

		fun errorDepartmentDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO i/o delete error",
				description = "Ошибка удаления отдела"
			)
		)

		fun errorDepartmentNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "$REPO not found",
				description = "Отдел не найден"
			)
		)

		fun errorDepartmentUpdate() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "bad update",
				description = "Ошибка обновления данных отдела"
			)
		)
	}
}