package ru.medals.ktor.auth.old

/*
fun Route.register(
	userService: UserService,
	authService: AuthService
) {

//    authenticate("register") {

	post("api/auth/register") {

//		val principal = call.principal<CreateOwnerRequest>()

		val request = call.receiveNullable<RegisterOwnerRequest>() ?: kotlin.run {
			call.respond(HttpStatusCode.BadRequest)
			return@post
		}

		*/
/*		if (request.email != principal?.email || request.login != principal.login) {
					call.respond(HttpStatusCode.Forbidden)
					return@post
				}*//*


		*/
/*		if (userService.doesUserWithLoginExist(request.login)) {
					call.respond(
						BasicApiResponse<Unit>(
							successful = false,
							message = ApiResponseMessages.USER_LOGIN_EXISTS
						)
					)
					return@post
				}*//*


		if (userService.doesUserWithEmailExist(request.email)) {
			responseBadRequest(message = ApiResponseMessages.USER_EMAIL_EXISTS)
			return@post
		}

		*/
/*		val regCode = authService.getRegCodeByEmail(request.email)
				if (regCode != request.code) {
					call.respond(
						BasicApiResponse<Unit>(
							successful = false,
							message = ApiResponseMessages.CODE_NOT_VALID
						)
					)
					return@post
				}*//*


		val err = userService.validateCreateAccountRequest(request)
		if (err == UserService.ValidationEvent.ErrorFieldEmpty) {
			responseBadRequest(message = ApiResponseMessages.FIELDS_BLANK)
			return@post
		}

		val hashPassword = hashPassword(request.password)
		val user = User(
			email = request.email,
			hashPassword = hashPassword,
			role = User.OWNER,
		)

		if (userService.createOwner(user)) {
			val refreshToken = authService.refreshToken(user)
			val accessToken = authService.accessToken(user)
			call.respond(
				HttpStatusCode.OK,
				AuthResponse(
					user = user,
					refreshToken = refreshToken,
					accessToken = accessToken
				)
			)
		} else {
			responseInternalServerError()
		}
	}
//    }
}*/
