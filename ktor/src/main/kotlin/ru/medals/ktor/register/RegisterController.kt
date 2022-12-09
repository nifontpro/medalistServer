package ru.medals.ktor.register

import io.ktor.server.application.*
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.medals.domain.register.bussines.processor.RegisterProcessor
import ru.medals.ktor.auth.model.response.AuthResponse
import ru.medals.ktor.core.process
import ru.medals.ktor.register.mappers.fromTransport
import ru.medals.ktor.register.mappers.toTransportAuthResponse
import ru.medals.ktor.register.mappers.toTransportExpDate
import ru.medals.ktor.register.model.request.CreateTempOwnerRequest
import ru.medals.ktor.register.model.request.CreateValidOwnerRequest
import ru.medals.ktor.register.model.request.RestorePasswordRequest

suspend fun ApplicationCall.registerTempOwner(processor: RegisterProcessor) =
	process<CreateTempOwnerRequest, Long, RegisterContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportExpDate() }
	)

suspend fun ApplicationCall.registerValidOwner(processor: RegisterProcessor) =
	process<CreateValidOwnerRequest, AuthResponse, RegisterContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportAuthResponse() }
	)

suspend fun ApplicationCall.restorePassword(processor: RegisterProcessor) =
	process<RestorePasswordRequest, Long, RegisterContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportExpDate() }
	)