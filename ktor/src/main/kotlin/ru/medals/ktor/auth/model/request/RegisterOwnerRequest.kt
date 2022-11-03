package ru.medals.ktor.auth.model.request

import io.ktor.server.auth.*

data class RegisterOwnerRequest(
	val email: String = "",
//    val login: String,
	val password: String = "",
//    val code: String = ""
) : Principal
