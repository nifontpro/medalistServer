package ru.medals.ktor.auth.mappers

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.ktor.auth.model.request.LoginRequest
import ru.medals.ktor.auth.model.request.RefreshRequest
import ru.medals.ktor.auth.model.request.RegisterOwnerRequest

fun AuthContext.fromTransport(request: RegisterOwnerRequest) {
	command = AuthContext.Command.REGISTER_OWNER
	email = request.email
	password = request.password
}

fun AuthContext.fromTransport(request: LoginRequest) {
	command = AuthContext.Command.LOGIN
	email = request.email
	password = request.password
}

@Suppress("UNUSED_PARAMETER")
fun AuthContext.fromTransport(request: RefreshRequest) {
	command = AuthContext.Command.REFRESH
}