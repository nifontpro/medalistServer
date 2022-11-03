package ru.medals.ktor.user

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.user.bussines.processor.UserProcessor
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.model.User.Companion.USER

fun Route.userRoutes() {
	val userProcessor: UserProcessor by inject()

	route("api/user") {
		authenticate(DIRECTOR) {
			post("create") {
				call.createUser(userProcessor)
			}

			delete {
				call.deleteUser(userProcessor)
			}
		}

		authenticate(USER) {
			put("/update") {
				call.updateUser(userProcessor)
			}

			put("image/update") {
				call.updateUserImageOld(userProcessor)
			}

			post("image") {
				call.createUserImage(userProcessor)
			}

			put("image") {
				call.updateUserImage(userProcessor)
			}

			delete("image") {
				call.deleteUserImage(userProcessor)
			}
		}

		authenticate(ADMIN) {
			post("/get_bosses") {
				call.getBosses(userProcessor)
			}
		}

		post("get_id") {
			call.getUserById(userProcessor)
		}

		/**
		 * Получить сотрудников отдела
		 */
		post("get_department") {
			call.getUsersByDepartment(userProcessor)
		}

		/**
		 * Получить лучших сотрудников компании
		 */
		post("get_best") {
			call.getBestUsersByCompany(userProcessor)
		}

		/**
		 * Получить количество сотрудников в компании
		 */
		post("/count_c") {
			call.getUserCountByCompany(userProcessor)
		}

		/**
		 * Получить количество сотрудников в отделе
		 */
		post("/count_d") {
			call.getUserCountByDepartment(userProcessor)
		}
	}
}