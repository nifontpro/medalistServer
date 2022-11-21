package ru.medals.domain.image.repository

import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.IImages

interface S3Repository {
	suspend fun putObject(key: String, fileData: FileData): String?
	suspend fun deleteObject(key: String): Boolean
	suspend fun available(): Boolean
	suspend fun deleteAllImages(entity: IImages): Boolean
}