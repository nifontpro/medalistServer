package ru.medals.ktor.core

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.reflect.*
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.ktor.core.response.responseBadRequest
import kotlin.reflect.full.createInstance

/**
 * Функционал запрос-ответ без авторизации
 */
suspend inline fun <reified T, reified R, reified C : BaseContext> ApplicationCall.process(
	processor: IBaseProcessor<C>,
	noinline fromTransport: C.(request: T) -> Unit,
	noinline toTransport: C.() -> R? = { null }
) {
	val context = C::class.createInstance()
	processTypes(
		context = context,
		requestType = TypeInfo(T::class, T::class.java),
		responseType = TypeInfo(R::class, R::class.java),
		processor = processor,
		fromTransport = fromTransport,
		toTransport = toTransport
	)
}

suspend fun <T, R, C : BaseContext> ApplicationCall.processTypes(
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

	val request = receiveNullable<T>(typeInfo = requestType) ?: run {
		responseBadRequest()
		return
	}
	context.fromTransport(request)

	processor.exec(context)

	mainResponseTypes(
		responseType = responseType,
		state = context.state,
		data = context.toTransport(),
		errors = context.errors,
	)
}