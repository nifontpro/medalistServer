package ru.medals.ktor.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.koin.ktor.ext.inject
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.ADMIN_PRIORITY
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.model.User.Companion.DIRECTOR_PRIORITY
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.medals.domain.user.model.User.Companion.ROOT
import ru.medals.domain.user.model.User.Companion.USER
import ru.medals.domain.user.model.User.Companion.USER_PRIORITY
import ru.medals.domain.user.repository.UserRepository
import ru.medals.ktor.auth.repository.AuthVerify
import ru.medals.ktor.user.toPrincipalUser

fun Application.configureSecurity() {

	val authVerify: AuthVerify by inject()
	val userRepository: UserRepository by inject()
	val config: HoconApplicationConfig by inject()

//	val audience = config.property("jwt.audience").getString()
	val jwtRealm = config.property("jwt.realm").getString()

	authentication {

		jwt(ROOT) {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)
			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "access") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				if (user.role != ROOT) return@validate null
				user.toPrincipalUser()
			}
		}

		jwt(OWNER) {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)

			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "access") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				if (user.role != OWNER) return@validate null
				user.toPrincipalUser()
			}
		}

		jwt(ADMIN) {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)

			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "access") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				if (user.getRolePriority() < ADMIN_PRIORITY) return@validate null
				user.toPrincipalUser()
			}
		}

		jwt(DIRECTOR) {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)

			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "access") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				if (user.getRolePriority() < DIRECTOR_PRIORITY) return@validate null
				user.toPrincipalUser()
			}
		}

		jwt(USER) {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)

			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "access") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				if (user.getRolePriority() < USER_PRIORITY) return@validate null
				user.toPrincipalUser()
			}
		}

		jwt("refresh") {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)

			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "refresh") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				user.toPrincipalUser()
			}
		}

		jwt("access") {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)

			validate { jwtCredential ->
				val payload = jwtCredential.payload
				val type = payload.getClaim("type").asString()
				if (type != "access") return@validate null
				val userId = payload.getClaim("userId").asString()
				val user = userRepository.getUserById(userId) ?: return@validate null
				user.toPrincipalUser()
			}
		}


	/*	jwt("register") {
			realm = jwtRealm
			verifier(authVerify.verifyJwtToken)
			validate { jwtCredential ->
				val payload = jwtCredential.payload
				if (payload.audience.contains(audience)) {
					val type = payload.getClaim("type").asString()
					if (type != "register") return@validate null
					val email = payload.getClaim("email").asString()
					RegisterOwnerRequest(email = email)
				} else {
					null
				}
			}
		}*/
	}
}
