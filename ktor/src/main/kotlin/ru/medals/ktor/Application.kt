package ru.medals.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import ru.medals.ktor.plugins.*

fun main(args: Array<String>): Unit =
	io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

	configureCORS()
	configureKoin()
	configureSerialization()
	configureSecurity()
	configureRouting()
//	configureSockets()
	configureMonitoring()

	log.info("START KTOR")
}
