package ru.medals.ktor.register

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.register.bussines.processor.RegisterProcessor

fun Route.registerRoutes() {

	val registerProcessor: RegisterProcessor by inject()

	route("register/owner") {

		post("temp") {
			call.registerTempOwner(registerProcessor)
		}

		post("valid") {
			call.registerValidOwner(registerProcessor)
		}

	}
}