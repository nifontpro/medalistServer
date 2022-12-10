package ru.medals.ktor.register

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.register.bussines.processor.RegisterProcessor

fun Route.registerRoutes() {

	val registerProcessor: RegisterProcessor by inject()

	route("register") {

		post("owner/temp") {
			call.registerTempOwner(registerProcessor)
		}

		post("owner/valid") {
			call.registerValidOwner(registerProcessor)
		}

		/**
		 * Запрос на сброс пароля с отправкой на почту ссылки для сброса
		 */
		post("psw/restore") {
			call.restorePassword(registerProcessor)
		}

		/**
		 * Сброс пароля по коду, полученному из письма
		 * (Код и id сотрудника отправляет фронтенд, извлекая их из ссылки)
		 */
		post("psw/reset") {
			call.resetPassword(registerProcessor)
		}

	}
}