package ru.medals.domain.image.repository

interface ImageRepository {

	suspend fun create(fileData: ByteArray, fileName: String?): String?
	suspend fun delete(imageId: String): Boolean
	suspend fun getDataById(imageId: String): ByteArray?
	suspend fun update(imageId: String, fileData: ByteArray, fileName: String?): Boolean

	suspend fun createTime(timestamp: Long): String?
	suspend fun getTimeById(id: String): Long?
}