package ru.medals.domain.register.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.auth.repository.AuthRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.register.model.TempReg
import ru.medals.domain.register.repository.RegisterRepository
import ru.medals.domain.user.model.User
import ru.medals.domain.user.repository.UserRepository

data class RegisterContext(

	var user: User = User(),
	var tempReg: TempReg = TempReg(), // Данные временной регистрации

	var code: String = "", // Код подтверждения, высланный на email
	var refreshToken: String = "",
	var accessToken: String = "",

	) : BaseContext(command = Command.NONE) {

	val authRepository: AuthRepository by inject(AuthRepository::class.java)
	val registerRepository: RegisterRepository by inject(RegisterRepository::class.java)
	val userRepository: UserRepository by inject(UserRepository::class.java)

	enum class Command : IBaseCommand {
		NONE,
		TEMP_REGISTER_OWNER,
		VALID_REGISTER_OWNER,
	}
}