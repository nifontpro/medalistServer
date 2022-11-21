package ru.medals.data.user.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

private const val REPO = "user"

fun errorUserGet() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "$REPO get error",
		description = "Ошибка получения сотрудников"
	)
)

fun errorUserDelete() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "user delete error",
		description = "Ошибка удаления сотрудника"
	)
)

fun errorUserNotFound() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "user not found",
		description = "Сотрудник не найден"
	)
)

fun errorUserUpdate() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "bad update",
		description = "Ошибка обновления данных сотрудника"
	)
)