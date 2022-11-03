package ru.medals.ktor.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.auth.bussines.processor.AuthProcessor

fun Route.authRoutes() {

	val authProcessor: AuthProcessor by inject()

	route("api/auth") {
		post("register") {
			call.registerOwner(authProcessor)
		}
		post("login") {
			call.loginUser(authProcessor)
		}
		authenticate("refresh") {
			post("refresh") {
				call.refreshToken(authProcessor)
			}
		}
	}
}