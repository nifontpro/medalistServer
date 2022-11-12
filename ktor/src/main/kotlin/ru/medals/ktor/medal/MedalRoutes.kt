package ru.medals.ktor.medal

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.medal.bussines.processor.MedalProcessor
import ru.medals.domain.user.model.User.Companion.ADMIN

fun Route.medalRoutes() {

	val medalProcessor: MedalProcessor by inject()

	route("medal") {

		authenticate(ADMIN) {

			post("create") {
				call.createMedal(medalProcessor)
			}

			delete {
				call.deleteMedal(medalProcessor)
			}

			put("update") {
				call.updateMedal(medalProcessor)
			}

			put("update/image") {
				call.updateMedalImageOld(medalProcessor)
			}

			post("image") {
				call.createMedalImage(medalProcessor)
			}

			put("image") {
				call.updateMedalImage(medalProcessor)
			}

			delete("image") {
				call.deleteMedalImage(medalProcessor)
			}

		}

		post("get_id") {
			call.getMedalById(medalProcessor)
		}
		post("get_company") {
			call.getMedalsByCompany(medalProcessor)
		}
		post("count_c") {
			call.getCountMedalsByCompany(medalProcessor)
		}

	}
}