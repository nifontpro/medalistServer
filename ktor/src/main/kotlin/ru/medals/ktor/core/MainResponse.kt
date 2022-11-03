package ru.medals.ktor.core

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.BaseError
import ru.medals.domain.core.bussines.helper.ContextError

suspend inline fun <reified T> ApplicationCall.mainResponse(
	state: ContextState,
	data: T?,
	errors: List<ContextError>
) {
	when (state) {
		ContextState.FINISHING -> {
			if (data != null) respond(HttpStatusCode.OK, data) else respond(HttpStatusCode.OK)
		}

		else -> respond(HttpStatusCode.Conflict, BaseError(errors = errors))
	}
}

suspend fun <T> ApplicationCall.mainResponseTypes(
	responseType: TypeInfo,
	state: ContextState,
	data: T?,
	errors: List<ContextError>
) {
	when (state) {
		ContextState.FINISHING -> {
			if (data != null) respond(HttpStatusCode.OK, data, messageType = responseType)
			else respond(HttpStatusCode.OK)
		}

		else -> respond(HttpStatusCode.Conflict, BaseError(errors = errors))
	}
}