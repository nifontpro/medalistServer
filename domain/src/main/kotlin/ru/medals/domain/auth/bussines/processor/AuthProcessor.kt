package ru.medals.domain.auth.bussines.processor

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.auth.bussines.validate.validatePassword
import ru.medals.domain.auth.bussines.validate.validateUserLoginEmpty
import ru.medals.domain.auth.bussines.validate.validateUserPasswordEmpty
import ru.medals.domain.auth.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.finishOperation
import ru.medals.domain.core.bussines.workers.initStatus
import ru.medals.domain.core.bussines.workers.operation
import ru.otus.cor.rootChain
import ru.otus.cor.worker

@Suppress("RemoveExplicitTypeArguments")
class AuthProcessor : IBaseProcessor<AuthContext> {

	override suspend fun exec(ctx: AuthContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<AuthContext> {
			initStatus("Инициализация статуса")

			operation("Вход", AuthContext.Command.LOGIN) {
//				validateUserEmailEmpty("Проверка, не пустой ли email")
				validateUserLoginEmpty("Проверка, не пустой ли Логин")
				validateUserPasswordEmpty("Проверка, не пустой ли password")
				trimFieldLogin("Очищаем логин")
				trimFieldPassword("Очищаем пароль")
//				getUserByEmailFromDb("Получаем пользователя по email")
				getUserByLoginFromDb("Получаем пользователя по login")
				validatePassword("Проверяем пароль")
				generateTokens("Генерируем токены")
			}

			operation("Обновить refresh-токен", AuthContext.Command.REFRESH) {
				worker("Получаем пользователя") { user = principalUser }
				generateTokens("Генерируем токены")
			}

			finishOperation()
		}.build()
	}
}