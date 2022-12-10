package ru.medals.ktor.register.model.request

data class ResetPasswordRequest(
	val userId: String? = null,
	val code: String? = null,
	val password: String? = null,
)
