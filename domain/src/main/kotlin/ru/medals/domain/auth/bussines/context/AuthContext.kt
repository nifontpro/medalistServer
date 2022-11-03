package ru.medals.domain.auth.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.auth.repository.AuthService
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.user.model.User
import ru.medals.domain.user.repository.UserRepository

data class AuthContext(

	var login: String = "",
	var email: String = "",
	var password: String = "",
	var code: String = "",

	var user: User = User(),
	var refreshToken: String = "",
	var accessToken: String = "",

	) : BaseContext(command = Command.NONE) {

	val userRepository: UserRepository by inject(UserRepository::class.java)
	val authService: AuthService by inject(AuthService::class.java)

	enum class Command : IBaseCommand {
		NONE,
		REGISTER_OWNER,
		LOGIN,
		REFRESH
	}
}