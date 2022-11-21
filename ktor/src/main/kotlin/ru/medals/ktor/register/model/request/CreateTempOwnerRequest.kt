package ru.medals.ktor.register.model.request

data class CreateTempOwnerRequest(
	val name: String? = null,
	val login: String? = null,
	val email: String? = null,
	val password: String? = null,
)
