package ru.medals.ktor.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
	install(CORS) {
		allowMethod(HttpMethod.Put)
		allowMethod(HttpMethod.Options)
		allowMethod(HttpMethod.Delete)

		allowHeader(HttpHeaders.Authorization)
		allowHeader(HttpHeaders.ContentType)
		allowHeader(HttpHeaders.AccessControlAllowHeaders)
		allowHeader(HttpHeaders.AccessControlAllowOrigin)

		exposeHeader(HttpHeaders.Authorization)
		exposeHeader(HttpHeaders.ContentType)
		exposeHeader(HttpHeaders.AccessControlAllowHeaders)
		exposeHeader(HttpHeaders.AccessControlAllowOrigin)

		allowCredentials = true
		allowNonSimpleContentTypes = true
		allowSameOrigin = true
//        allowHost("localhost:3000", listOf("http", "https"))
//               allowHost("*", listOf("http", "https"))
		anyHost()
	}
//    install(DefaultHeaders) {
//        header(HttpHeaders.AccessControlAllowOrigin, "*/*")
//        header(HttpHeaders.AccessControlAllowOrigin, "*")
//        header(HttpHeaders.AccessControlAllowOrigin, "http://localhost:3000")
//    }
}