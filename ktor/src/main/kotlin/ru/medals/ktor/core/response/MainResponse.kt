package ru.medals.ktor.core.response

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.medals.domain.core.bussines.helper.BaseError
import ru.medals.domain.core.bussines.helper.ContextError

suspend fun ApplicationCall.responseBadRequest(message: String = "Неверный запрос") {
	respond(
		status = HttpStatusCode.BadRequest, BaseError(
			errors = listOf(
				ContextError(
					code = "bad request",
					group = "request",
					message = message
				)
			)
		)
	)
}

suspend fun ApplicationCall.responseUnauthorized(message: String = "Нет доступа") {
	respond(
		status = HttpStatusCode.Unauthorized, BaseError(
			errors = listOf(
				ContextError(
					code = "Unauthorized",
					group = "request",
					message = message
				)
			)
		)
	)
}

suspend fun ApplicationCall.responseInternalError(message: String = "Внутренняя ошибка сервера") {
	respond(
		status = HttpStatusCode.InternalServerError, BaseError(
			errors = listOf(
				ContextError(
					code = "InternalServerError",
					group = "request",
					message = message
				)
			)
		)
	)
}