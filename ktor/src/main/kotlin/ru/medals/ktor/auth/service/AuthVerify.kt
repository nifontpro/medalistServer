package ru.medals.ktor.auth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*

class AuthVerify(
	config: HoconApplicationConfig,
) {

	private val issuer = config.property("jwt.issuer").getString()
	private val audience = config.property("jwt.audience").getString()
	private val secret = System.getenv("JWT_SECRET")
	private val algorithm = Algorithm.HMAC512(secret)

	val verifyJwtToken: JWTVerifier = JWT
		.require(algorithm)
		.withAudience(audience)
		.withIssuer(issuer)
		.build()
}