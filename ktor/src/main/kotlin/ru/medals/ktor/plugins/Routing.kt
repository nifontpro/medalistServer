package ru.medals.ktor.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.medals.domain.core.util.Constants.LOCAL_FOLDER
import ru.medals.ktor.activity.activityRoutes
import ru.medals.ktor.auth.authRoutes
import ru.medals.ktor.award.awardRoutes
import ru.medals.ktor.company.companyRoutes
import ru.medals.ktor.department.departmentRoutes
import ru.medals.ktor.medal.medalRoutes
import ru.medals.ktor.message.messageRoutes
import ru.medals.ktor.register.registerRoutes
import ru.medals.ktor.user.userRoutes
import java.io.File

fun Application.configureRouting() {

	routing {

		route("api") {
			authRoutes()
			registerRoutes()
			userRoutes()
			companyRoutes()
			departmentRoutes()
			medalRoutes()
			awardRoutes()
			messageRoutes()
			activityRoutes()

			get("/") {
				call.respondText("Medalist server MVP")
			}

			static("/logs") {
				staticRootFolder = File(LOCAL_FOLDER)
				files(".")
			}
		}

		/*
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
