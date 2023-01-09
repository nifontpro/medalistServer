package ru.medals.ktor.activity

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.activity.bussines.processor.ActivityProcessor

fun Route.activityRoutes() {

	val activityProcessor: ActivityProcessor by inject()

	route("activity") {

		/**
		 * Получить активность в компании
		 */
		post("get_c") {
			call.getActivitiesByCompany(activityProcessor)
		}
	}
}
