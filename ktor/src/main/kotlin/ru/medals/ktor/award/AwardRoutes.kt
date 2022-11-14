package ru.medals.ktor.award

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.award.bussines.processor.AwardProcessor
import ru.medals.domain.user.model.User.Companion.ADMIN

fun Route.awardRoutes() {

	val awardProcessor: AwardProcessor by inject()

	route("award") {
		authenticate(ADMIN) {
			post("create") {
				call.createAward(awardProcessor)
			}

			delete {
				call.deleteAward(awardProcessor)
			}

			put {
				call.updateAward(awardProcessor)
			}
		}

		post("get_id") {
			call.getAwardById(awardProcessor)
		}

		post("get") {
			call.getAwardsByCompany(awardProcessor)
		}

	}
}
