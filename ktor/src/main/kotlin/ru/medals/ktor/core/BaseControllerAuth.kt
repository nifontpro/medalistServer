package ru.medals.ktor.core

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.util.reflect.*
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.ktor.core.response.responseBadRequest
import ru.medals.ktor.core.response.responseUnauthorized
import ru.medals.ktor.user.PrincipalUser
import kotlin.reflect.full.createInstance

/**
 * Функционал запрос-ответ сервера с применением бизнес-логики с авторизацией
 */
suspend inline fun <reified T, reified R, reified C : BaseContext> ApplicationCall.authProcess(
	processor: IBaseProcessor<C>,
	noinline fromTransport: C.(request: T) -> Unit,
	noinline toTransport: C.() -> R? = { null }
) {
	val context = C::class.createInstance()
	authProcessTypes(
		context = context,
		requestType = TypeInfo(T::class, T::class.java),
		responseType = TypeInfo(R::class, R::class.java),
		processor = processor,
		fromTransport = fromTransport,
		toTransport = toTransport
	)
}

suspend fun <T, R, C : BaseContext> ApplicationCall.authProcessTypes(
	context: C,
	requestType: TypeInfo,
	responseType: TypeInfo,
	processor: IBaseProcessor<C>,
	fromTransport: C.(request: T) -> Unit,
	toTransport: C.() -> R? = { null }
) {
	context.apply {
		this.timeStart = System.currentTimeMillis()
	}

	val principalUser = principal<PrincipalUser>() ?: run {
		responseUnauthorized("Не найден авторизованный пользователь")
		return
	}
	context.principalUser = principalUser.toUser()

	val request = try {
		receive<T>(typeInfo = requestType)
	} catch (e: Exception) {
		responseBadRequest()
		return
	}

	println("AUTH REQUEST OK: $request")

	context.fromTransport(request)

	processor.exec(context)

	mainResponseTypes(
		responseType = responseType,
		state = context.state,
		data = context.toTransport(),
		errors = context.errors,
	)
}