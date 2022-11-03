package ru.medals.ktor.auth.mappers

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.ktor.auth.model.response.AuthResponse

fun AuthContext.toTransportAuthResponse(): AuthResponse {
	return AuthResponse(
		user = user.copy(hashPassword = null),
		refreshToken = refreshToken,
		accessToken = accessToken
	)
}
