package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.sendCodeToUserEmail(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		code = (10000..99999).random().toString()
		val message = "Привет, ${user.name}! Для завершения регистрации, введите код: $code"
		checkRepositoryBool(
			repository = "email",
			description = "Ошибка при отправке кода подтверждения на почту ${user.email}"
		) {
			authRepository.sendEmail(
				message = message,
				toEmail = user.email ?: ""
			)
		}
	}
}
