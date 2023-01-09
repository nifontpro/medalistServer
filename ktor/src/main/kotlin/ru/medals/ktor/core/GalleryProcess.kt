package ru.medals.ktor.core

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor
import ru.medals.domain.gallery.model.Gallery
import ru.medals.domain.image.model.FileData
import ru.medals.ktor.core.response.responseBadRequest
import ru.medals.ktor.core.response.responseInternalError
import ru.medals.ktor.core.response.responseUnauthorized
import ru.medals.ktor.user.PrincipalUser
import java.io.File

/**
 * Добавление элемента в галлерею объектов
 */
suspend fun ApplicationCall.processGallery(
	context: GalleryContext,
	processor: GalleryProcessor
) {
	context.apply {
		this.timeStart = System.currentTimeMillis()
	}

	val principalUser = principal<PrincipalUser>() ?: run {
		responseUnauthorized("Не найден авторизованный пользователь")
		return
	}
	context.principalUser = principalUser.toUser()

	val multipart = receiveMultipart()
	var fileData: FileData? = null
	var gallery = Gallery()

	multipart.forEachPart { partData ->
		when (partData) {
			is PartData.FormItem -> {
				val data = partData.value
				gallery = when (partData.name) {

					"name" -> gallery.copy(name = data)
					"description" -> gallery.copy(description = data)
					"folderId" -> gallery.copy(folderId = data)

					else -> {
						responseBadRequest("Неверный формат запроса partData")
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

	if (fileData != null) {
		fileData?.let { context.fileData = it }
	} else {
		responseInternalError("Ошибка записи в файловую систему сервера")
		return
	}

	processor.exec(context)

	fileData?.url?.let { File(it).delete() }

	mainResponse(
		state = context.state,
		data = context.responseId,
		errors = context.errors,
	)
}