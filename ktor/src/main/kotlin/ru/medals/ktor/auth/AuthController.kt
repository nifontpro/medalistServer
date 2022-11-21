package ru.medals.ktor.auth

import io.ktor.server.application.*
import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.auth.bussines.processor.AuthProcessor
import ru.medals.ktor.auth.mappers.fromTransport
import ru.medals.ktor.auth.mappers.toTransportAuthResponse
import ru.medals.ktor.auth.model.request.LoginRequest
import ru.medals.ktor.auth.model.request.RefreshRequest
import ru.medals.ktor.auth.model.response.AuthResponse
import ru.medals.ktor.core.authProcess
import ru.medals.ktor.core.process

suspend fun ApplicationCall.loginUser(processor: AuthProcessor) =
	process<LoginRequest, AuthResponse, AuthContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportAuthResponse() }
	)

suspend fun ApplicationCall.refreshToken(processor: AuthProcessor) =
	authProcess<RefreshRequest, AuthResponse, AuthContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportAuthResponse() }
	)