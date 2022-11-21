package ru.medals.ktor.register.mappers

import ru.medals.domain.register.bussines.context.RegisterContext
import ru.medals.ktor.auth.model.response.AuthResponse

fun RegisterContext.toTransportAuthResponse(): AuthResponse {
	return AuthResponse(
		user = user.copy(hashPassword = null),
		refreshToken = refreshToken,
		accessToken = accessToken
	)
}
