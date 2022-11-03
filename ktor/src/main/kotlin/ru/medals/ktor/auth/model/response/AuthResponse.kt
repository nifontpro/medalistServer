package ru.medals.ktor.auth.model.response

import ru.medals.domain.user.model.User

data class AuthResponse(
	val user: User,
	val refreshToken: String,
	val accessToken: String,
)
