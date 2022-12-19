package ru.medals.ktor.user.model.request

data class UpdateUserPasswordRequest(
	val userId: String? = null,
	val password: String? = null,
	val newPassword: String? = null,
	val test: Boolean = false,
)