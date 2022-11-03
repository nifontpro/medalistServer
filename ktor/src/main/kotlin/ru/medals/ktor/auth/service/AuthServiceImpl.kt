package ru.medals.ktor.auth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail
import ru.medals.domain.auth.model.TempReg
import ru.medals.domain.auth.model.TokenLife
import ru.medals.domain.auth.repository.AuthRepository
import ru.medals.domain.auth.repository.AuthService
import ru.medals.domain.core.util.Constants.LIFE_TIME_ACCESS_TOKEN
import ru.medals.domain.core.util.Constants.LIFE_TIME_REFRESH_TOKEN
import ru.medals.domain.core.util.Constants.LIFE_TIME_REGISTER_TOKEN
import ru.medals.domain.user.model.User
import java.util.*

class AuthServiceImpl(
	private val config: HoconApplicationConfig,
	private val authRepository: AuthRepository
) : AuthService {

	private val issuer = config.property("jwt.issuer").getString()
	private val audience = config.property("jwt.audience").getString()
	private val secret = System.getenv("JWT_SECRET")
	private val algorithm = Algorithm.HMAC512(secret)

	override fun refreshToken(user: User): String {
		val expirationDate = System.currentTimeMillis() + LIFE_TIME_REFRESH_TOKEN
		return JWT.create()
			.withAudience(audience)
			.withIssuer(issuer)
			.withClaim("userId", user.id)
			.withClaim("role", user.role)
			.withClaim("type", "refresh")
			.withExpiresAt(Date(expirationDate))
			.sign(algorithm)
	}

	override fun accessToken(user: User): String {
		val expirationDate = System.currentTimeMillis() + LIFE_TIME_ACCESS_TOKEN
		return JWT.create()
			.withAudience(audience)
			.withIssuer(issuer)
			.withClaim("userId", user.id)
			.withClaim("role", user.role)
			.withClaim("type", "access")
			.withExpiresAt(Date(expirationDate))
			.sign(algorithm)
	}

	override fun emailJwtToken(email: String): TokenLife {
		val expirationDate = System.currentTimeMillis() + LIFE_TIME_REGISTER_TOKEN
		val token = JWT.create()
			.withAudience(audience)
			.withIssuer(issuer)
			.withClaim("email", email)
			.withClaim("type", "register")
			.withExpiresAt(Date(expirationDate))
			.sign(algorithm)
		return TokenLife(token = token, expirationDate = expirationDate)
	}

	override fun sendEmail(
		message: String,
		toEmail: String
	) {

		/*val email = config.property("smtp.email").getString()
        val password = config.property("smtp.password").getString()*/

		val email = System.getenv("SMTP_EMAIL")
		val password = System.getenv("SMTP_PASSWORD")
		SimpleEmail().apply {
			hostName = config.property("smtp.hostName").getString()
			setSmtpPort(config.property("smtp.smtpPort").getString().toInt())
			setAuthenticator(DefaultAuthenticator(email, password))
			isSSLOnConnect = true
			setFrom(email)
			subject = config.property("smtp.subject").getString()
			setMsg(message)
			addTo(toEmail)
			send()
		}
	}

	override suspend fun checkTempRegExist(email: String): Boolean {
		return authRepository.checkTempRegExist(email)
	}

	override suspend fun saveTempRegistrationEmail(email: String, code: String) {
		authRepository.createTempReg(
			TempReg(
				email = email,
				code = code,
				expDate = System.currentTimeMillis() + LIFE_TIME_REGISTER_TOKEN
			)
		)
	}

	override suspend fun getRegCodeByEmail(email: String): String? {
		return authRepository.getRegCodeByEmail(email)
	}
}