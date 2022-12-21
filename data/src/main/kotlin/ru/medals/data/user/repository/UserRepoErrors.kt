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

fun errorUserCountGet() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "$REPO get error",
		description = "Ошибка получения количества сотрудников"
	)
)

fun errorUserDelete() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "user delete error",
		description = "Ошибка удаления сотрудника"
	)
)

fun errorUserImageDelete() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "image delete error",
		description = "Ошибка удаления изображения у сотрудника"
	)
)

fun errorUserImageNotFound() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "image not found",
		description = "У сотрудника отсутствует изображение"
	)
)

fun errorUserNotFound() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "user not found",
		description = "Сотрудник не найден"
	)
)

fun errorUserCreate() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "bad create",
		description = "Ошибка создания профиля сотрудника"
	)
)

fun errorUserUpdate() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "bad update",
		description = "Ошибка обновления данных сотрудника"
	)
)

fun errorUserAwardCountUpdate() = RepositoryData.error(
	error = RepositoryError(
		repository = REPO,
		violationCode = "bad update",
		description = "Ошибка при обновлении количества наград сотрудника"
	)
)