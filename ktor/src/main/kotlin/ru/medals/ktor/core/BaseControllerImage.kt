package ru.medals.ktor.core

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.image.model.FileData
import ru.medals.ktor.core.response.responseBadRequest
import ru.medals.ktor.core.response.responseInternalError
import ru.medals.ktor.core.response.responseUnauthorized
import ru.medals.ktor.user.PrincipalUser
import java.io.File

/**
 * Функционал запрос-ответ сервера с авторизацией для обновления изображения из multi part data
 */
suspend fun <C : BaseContext> ApplicationCall.processImage(
	context: C,
	processor: IBaseProcessor<C>
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
	var description: String? = null

	multipart.forEachPart { partData ->
		when (partData) {
			is PartData.FormItem -> {
				val data = partData.value
				when (partData.name) {

					"userId" -> context.userId = data
					"companyId" -> context.companyId = data
					"departmentId" -> context.departmentId = data
					"medalId" -> context.medalId = data

					"imageKey" -> context.imageKey = data
					"description" -> description = data

					else -> {
						responseBadRequest("Неверный формат запроса partData")
						return@forEachPart
					}
				}
			}

			is PartData.FileItem -> {
				if (partData.name == "imageUrl") {
					fileData = partData.save()
				}
			}

			is PartData.BinaryItem -> {}
			is PartData.BinaryChannelItem -> {}
		}
		// обязательно утилизируйте после использования, чтобы предотвратить утечку
		partData.dispose()
	}

	if (fileData != null) {
		fileData?.let { context.fileData = it.copy(description = description) }
	} else {
		responseInternalError("Ошибка записи в файловую систему сервера")
		return
	}

	processor.exec(context)

	fileData?.url?.let { File(it).delete() }

	mainResponse<Unit>(
		state = context.state,
		data = null,
		errors = context.errors,
	)
}

/**
 * Функционал запрос-ответ сервера с авторизацией для обновления изображения из multi part data
 */
suspend fun <C : BaseContext> ApplicationCall.processImageSingle(
	context: C,
	processor: IBaseProcessor<C>
) {
	context.apply {
		this.timeStart = System.currentTimeMillis()
	}

	val principalUser = principal<PrincipalUser>() ?: run {
		responseUnauthorized("Не найден авторизованный пользователь")
		return
	}
	context.principalUser = principalUser.toUser()

	val id = parameters["id"]
	if (id.isNullOrBlank()) {
		responseBadRequest()
		return
	}
	context.imageEntityId = id

	val multipart = receiveMultipart()
	var fileData: FileData? = null

	multipart.forEachPart { partData ->
		if (partData is PartData.FileItem) {
			if (partData.name == "imageUrl") {
				fileData = partData.save()
			}
		}
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

	mainResponse<Unit>(
		state = context.state,
		data = null,
		errors = context.errors,
	)
}