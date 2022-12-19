package ru.medals.ktor.auth.model.request

data class LoginRequest(
	val login: String = "",
//	val email: String = "",
	val password: String = ""
)