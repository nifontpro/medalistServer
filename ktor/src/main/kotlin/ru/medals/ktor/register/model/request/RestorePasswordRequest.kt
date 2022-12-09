package ru.medals.ktor.register.model.request

data class RestorePasswordRequest(
	val email: String? = null,
)
