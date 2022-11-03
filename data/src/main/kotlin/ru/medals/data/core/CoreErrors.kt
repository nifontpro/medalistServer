package ru.medals.data.core

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

fun errorS3() = RepositoryData.error(
	error = RepositoryError(
		repository = "s3",
		violationCode = "s3 error",
		description = "Ошибка хранилища объектов s3"
	)
)

fun errorBadImageKey(repository: String) = RepositoryData.error(
	error = RepositoryError(
		repository = repository,
		violationCode = "bad image key",
		description = "Неверный идентификатор изображения"
	)
)
