package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker
import java.util.*

fun ICorChainDsl<RegisterContext>.sendCodeToUserEmailForRestore(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		val host = "https://nmedalist.ru"
		val localhost = "http://localhost:3000"
		code = UUID.randomUUID().toString()
		val message = "Привет, ${user.lastname} ${user.name}, Login: ${user.login}! \n\n" +
				"Для завершения восстановления пароля, перейдите по ссылке: \n" +
				"$host/register/reset/$code/${user.id}\n\n" +
				"В среде разработки:\n" +
				"$localhost/register/reset/$code/${user.id}"
		checkRepositoryBool(
			repository = "email",
			description = "Ошибка при отправке письма для сброса пароля на почту ${user.email}"
		) {
			authRepository.sendEmail(
				message = message,
				toEmail = user.email ?: ""
			)
		}
	}
}
