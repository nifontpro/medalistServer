package ru.medals.ktor.register.mappers

import mu.KotlinLogging
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.medals.domain.user.model.User
import ru.medals.ktor.register.model.request.CreateTempOwnerRequest
import ru.medals.ktor.register.model.request.CreateValidOwnerRequest
import ru.medals.ktor.register.model.request.RestorePasswordRequest

private val log = KotlinLogging.logger {}

fun RegisterContext.fromTransport(request: CreateTempOwnerRequest) {
	command = RegisterContext.Command.TEMP_REGISTER_OWNER
	user = User(
		login = request.login,
		hashPassword = request.password,
		email = request.email,
		name = request.name,
	)
}

fun RegisterContext.fromTransport(request: CreateValidOwnerRequest) {
	command = RegisterContext.Command.VALID_REGISTER_OWNER
	user = User(
		email = request.email,
	)
	code = request.code ?: ""

	log.info("REGISTER OWNER VALID: ${request.email}")
}

fun RegisterContext.fromTransport(request: RestorePasswordRequest) {
	command = RegisterContext.Command.RESET_PASSWORD_EMAIL
	email = request.email
}