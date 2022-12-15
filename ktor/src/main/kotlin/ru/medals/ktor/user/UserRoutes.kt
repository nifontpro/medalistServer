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

	route("user") {
		authenticate(DIRECTOR) {
			post("create") {
				call.createUser(userProcessor)
			}

			delete {
				call.deleteUser(userProcessor)
			}
		}

		authenticate(USER) {
			put("update") {
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
			post("get_bosses") {
				call.getBosses(userProcessor)
			}
		}

		post("get_id") {
			call.getUserById(userProcessor)
		}

		post("get_id_dep") {
			call.getUserByIdWithDepName(userProcessor)
		}

		post("get_id_awards") {
			call.getUserByIdWithAwards(userProcessor)
		}

		/**
		 * Получить сотрудников отдела
		 */
		post("get_department") {
			call.getUsersByDepartment(userProcessor)
		}

		/**
		 * Получить сотрудников компании
		 */
		post("get_company") {
			call.getUsersByCompany(userProcessor)
		}

		post("get_company_dep") {
			call.getUsersByCompanyDepName(userProcessor)
		}

		post("get_awards") {
			call.getUsersWithAwards(userProcessor)
		}

		post("get_awards_full") {
			call.getUsersWithAwardsFull(userProcessor)
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
		post("count_c") {
			call.getUserCountByCompany(userProcessor)
		}

		/**
		 * Получить количество сотрудников в отделе
		 */
		post("count_d") {
			call.getUserCountByDepartment(userProcessor)
		}

		/**
		 * Получить количество наград сотрудников в отделе
		 */
		post("count_ad") {
			call.getUserAwardCountByDepartment(userProcessor)
		}

		/**
		 * Получить количество наград сотрудников в компании
		 */
		post("count_ac") {
			call.getUserAwardCountByCompany(userProcessor)
		}
		// on reset password
		/**
		 * Получить количество наград сотрудников в компании,
		 * аггрегированное по отделам
		 */
		post("count_ac_dep") {
			call.getUserAwardCountDepByCompany(userProcessor)
		}
		//on test1
	}
}