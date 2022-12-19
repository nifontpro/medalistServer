package ru.medals.domain.user.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.core.util.getFullUserName
import ru.medals.domain.user.bussines.context.UserContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<UserContext>.sendLinkToUserEmailWithPassword(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		val fullName = getFullUserName(user.lastname, user.name, user.patronymic)
		val message = "Привет, $fullName, Логин: ${user.login}! \n\n" +
				"Ваш пароль: $password\n" +
				"Сменить пароль можно в личном кабинете.\n" +
				"Для входа в приложение используйте Логин/Пароль."
		checkRepositoryBool(
			repository = "email",
			description = "Ошибка при отправке письма сотруднику на почту ${user.email}"
		) {
			authRepository.sendEmail(
				message = message,
				toEmail = user.email ?: ""
			)
		}
	}
}
