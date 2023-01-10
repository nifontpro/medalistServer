package ru.medals.ktor.core

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.domain.image.model.FileData
import ru.medals.ktor.core.response.responseBadRequest
import ru.medals.ktor.core.response.responseInternalError
import java.io.File

/**
 * Запрос для модификации объекта галлереи
 * */
suspend fun ApplicationCall.processGallery(
	context: GalleryContext,
	processor: GalleryProcessor
) {
	context.apply {
		this.timeStart = System.currentTimeMillis()
	}

	/*	val principalUser = principal<PrincipalUser>() ?: run {
			responseUnauthorized("Не найден авторизованный пользователь")
			return
		}
		context.principalUser = principalUser.toUser()*/

	val multipart = try {
		receiveMultipart()
	} catch (e: Exception) {
		responseBadRequest("Неверный формат запроса partData")
		return
	}

	var fileData: FileData? = null
	var galleryItem = GalleryItem()
	var isError = false

	multipart.forEachPart { partData ->
		when (partData) {
			is PartData.FormItem -> {
				val data = partData.value
				galleryItem = when (partData.name) {

					"id" -> galleryItem.copy(id = data)
					"name" -> galleryItem.copy(name = data)
					"description" -> galleryItem.copy(description = data)
					"folderId" -> galleryItem.copy(folderId = data)

					else -> {
						isError = true
						return@forEachPart
					}
				}
			}

			is PartData.FileItem -> {
				if (partData.name == "imageUrl") {
					fileData = partData.save()?.copy(system = true)
				}
			}

			is PartData.BinaryItem -> {}
			is PartData.BinaryChannelItem -> {}
		}
		// обязательно утилизируйте после использования, чтобы предотвратить утечку
		partData.dispose()
	}

	if (isError) {
		responseBadRequest("Неверный формат данных тела запроса")
		return
	}

	if (fileData != null) {
		fileData?.let { context.fileData = it }
	} else {
		responseInternalError("Ошибка записи в файловую систему сервера")
		return
	}

	context.galleryItem = galleryItem

	processor.exec(context)

	fileData?.url?.let { File(it).delete() }

	mainResponse(
		state = context.state,
		data = context.responseId,
		errors = context.errors,
	)
}