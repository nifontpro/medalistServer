package ru.medals.s3.repository

import com.amazonaws.services.s3.AmazonS3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.medals.domain.core.util.Constants
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.repository.S3Repository
import java.io.File

class S3RepositoryImpl(
	private val s3: AmazonS3
) : S3Repository {

	private fun getBucketName(system: Boolean) =
		if (system) Constants.S3_BUCKET_SYSTEM else Constants.S3_BUCKET_NAME

	override suspend fun putObject(key: String, fileData: FileData): String? {
		return try {
			val bucket = getBucketName(fileData.system)
			withContext(Dispatchers.IO) {
				val file = File(fileData.url)
				s3.putObject(bucket, key, file)
				s3.getUrl(bucket, key).toExternalForm()
			}
		} catch (e: Exception) {
			println(e.message)
			null
		}
	}

	override suspend fun deleteObject(key: String, system: Boolean): Boolean {
		return try {
			withContext(Dispatchers.IO) {
				s3.deleteObject(getBucketName(system), key)
			}
			true
		} catch (e: Exception) {
			println(e.message)
			false
		}
	}

	// Удаляем все изображения сущности
	override suspend fun deleteAllImages(entity: IImages): Boolean {
		return try {
			withContext(Dispatchers.IO) {
				entity.imageKey?.let {
					s3.deleteObject(Constants.S3_BUCKET_NAME, it)
				}
				entity.images.forEach { imageRef ->
					s3.deleteObject(Constants.S3_BUCKET_NAME, imageRef.imageKey)
				}
			}
			true
		} catch (e: Exception) {
			println(e.message)
			false
		}
	}

	/**
	 * Проверка доступности хранилища
	 */
	override suspend fun available(): Boolean {
		return try {
			withContext(Dispatchers.IO) {
				s3.listBuckets().map {
					println(it.name)
					it.name
				}.contains(Constants.S3_BUCKET_NAME)
			}
		} catch (e: Exception) {
			false
		}
	}

//		return suspendCoroutine {
//			it.resume(
//				try {
//					s3.deleteObject(Constants.S3_BUCKET_NAME, key)
//					true
//				} catch (e: Exception) {
//					println(e.stackTrace)
//					false
//				}
//			)
//		}
}