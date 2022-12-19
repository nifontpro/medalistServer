package ru.medals.ktor.auth.mappers

import mu.KotlinLogging
import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.ktor.auth.model.request.LoginRequest
import ru.medals.ktor.auth.model.request.RefreshRequest

private val log = KotlinLogging.logger {}

fun AuthContext.fromTransport(request: LoginRequest) {
	command = AuthContext.Command.LOGIN
	login = request.login
//	email = request.email
	password = request.password

	log.info("LOGIN: $login", request)

}

@Suppress("UNUSED_PARAMETER")
fun AuthContext.fromTransport(request: RefreshRequest) {
	command = AuthContext.Command.REFRESH
}