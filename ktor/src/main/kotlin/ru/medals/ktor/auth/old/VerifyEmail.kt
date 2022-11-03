package ru.medals.ktor.auth.old

/*
fun Route.verifyEmail(
	userRepository: UserRepository,
	authService: AuthService,
) {
	// setting yandex email auth:
	// https://mail.yandex.ru/?uid=203434031#setup/client

	post("api/auth/verify_email") {

		val request = call.receiveNullable<RegisterOwnerRequest>() ?: kotlin.run {
			call.respond(HttpStatusCode.BadRequest)
			return@post
		}

		if (request.email.isBlank() || request.password.isBlank()) {
			call.respond(HttpStatusCode.BadRequest)
			return@post
		}

		*/
/*        if (
								!verifyEmailPassword(
										login = request.login,
										hash = request.password
								)
						) {
								call.respond(HttpStatusCode.BadRequest)
								return@post
						}*//*


		*/
/*       if (userService.doesUserWithLoginExist(request.login)) {
							 call.respond(
									 BasicApiResponse<Unit>(
											 successful = false,
											 message = ApiResponseMessages.USER_LOGIN_EXISTS
									 )
							 )
							 return@post
					 }*//*


		if (userRepository.getUserByEmail(request.email) != null) {
			call.respond(
				BasicApiResponse<Unit>(
					successful = false,
					message = ApiResponseMessages.USER_EMAIL_EXISTS
				)
			)
			return@post
		}

		if (authService.checkTempRegExist(request.email)) {
			call.respond(
				BasicApiResponse<Unit>(
					successful = false,
					message = ApiResponseMessages.USER_ALREADY_REGISTER
				)
			)
			return@post
		}

		val code = (10000..99999).random().toString()
//        val hashCode = BCrypt.hashpw(code, BCrypt.gensalt())

		val message = "Hello! Registration code: $code"

		try {
			authService.sendEmail(message, request.email)

			val tokenLife = authService.emailJwtToken(email = request.email)

			call.respond(
				HttpStatusCode.OK,
				BasicApiResponse(
					successful = true,
					data = tokenLife,
				)
			)
			authService.saveTempRegistrationEmail(request.email, code)
		} catch (e: Exception) {
			call.respond(
				HttpStatusCode.OK,
				BasicApiResponse<Unit>(
					successful = false,
					message = ApiResponseMessages.SEND_EMAIL_ERROR
				)
			)
		}
	}
}*/
