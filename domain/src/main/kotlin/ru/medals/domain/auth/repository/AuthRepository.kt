package ru.medals.domain.auth.repository

import ru.medals.domain.auth.model.TokenLife
import ru.medals.domain.user.model.User

interface AuthRepository {

	fun refreshToken(user: User): String
	fun accessToken(user: User): String
	fun emailJwtToken(email: String): TokenLife
	fun sendEmail(message: String, toEmail: String): Boolean

}