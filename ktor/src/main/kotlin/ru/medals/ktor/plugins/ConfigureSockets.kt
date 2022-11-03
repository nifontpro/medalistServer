package ru.medals.ktor.plugins

import com.auth0.jwt.JWTVerifier
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import ru.medals.ktor.auth.service.AuthVerify
import java.time.Duration
import java.util.*

fun emitFlow(): Flow<String> = flow {
	var x = 0
	while (true) {
		x++
		emit("--- Message $x")
		delay(3000)
	}
}

// https://github.com/auth0/java-jwt
fun Application.configureSockets() {

	val authVerify: AuthVerify by inject()
	val verifier: JWTVerifier = authVerify.verifyJwtToken

	install(WebSockets) {
		pingPeriod = Duration.ofSeconds(15)
		timeout = Duration.ofSeconds(15)
		maxFrameSize = Long.MAX_VALUE
		masking = false
	}

	routing {
		val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
//		authenticate(USER) {
		webSocket("/chat") {
			val thisConnection = Connection(this)
			connections += thisConnection
			println("Adding user! ${thisConnection.name}")
			try {
				send("Server: verify jwt token...")
				val msg = incoming.receive()
				if (msg is Frame.Text) {
					val token = msg.readText()
					verifier.verify(token)
					send("Token valid! You are connected! There are ${connections.count()} users here.")
				}

				launch {
					emitFlow().collect { message ->
						send(message)
					}
				}

				incoming
					.receiveAsFlow()
					.mapNotNull { it as? Frame.Text }
					.map { textFrame ->
						val receivedText = textFrame.readText()
						val textWithUsername = "[${thisConnection.name}/${connections.count()}]: $receivedText"
						connections.forEach {
							it.session.send(textWithUsername)
							println(textWithUsername)
						}
					}.collect()
				println("--------------->End Web socket!")
			} catch (e: Exception) {
				println(e.localizedMessage)
			} finally {
				println("Removing ${thisConnection.name}!")
				connections -= thisConnection
			}
		}
//		}
	}
}