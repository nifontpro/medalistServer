package ru.medals.ktor.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.medals.ktor.auth.authRoutes
import ru.medals.ktor.award.awardRoutes
import ru.medals.ktor.company.companyRoutes
import ru.medals.ktor.department.departmentRoutes
import ru.medals.ktor.medal.medalRoutes
import ru.medals.ktor.reward.rewardRoutes
import ru.medals.ktor.user.userRoutes

fun Application.configureRouting() {

	routing {

		route("api") {
			authRoutes()
			userRoutes()
			companyRoutes()
			departmentRoutes()
			medalRoutes()
			rewardRoutes()
			awardRoutes()

			get("/") {
				call.respondText("Medals server 2022 MVP")
			}
		}

	/*	static("/") {
			staticRootFolder = File(LOCAL_FOLDER)
			files(".")
		}

		val imageRepository: ImageRepository by inject()

		get("api/image/{id}") {
			val id = call.parameters["id"] ?: kotlin.run {
				call.respond(HttpStatusCode.BadRequest)
				return@get
			}
			val fileData = imageRepository.getDataById(id) ?: kotlin.run {
				call.respond(HttpStatusCode.NotFound)
				return@get
			}
			call.respondBytes(fileData)
		}*/
	}
}
