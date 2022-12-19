package ru.medals.domain.register.bussines.processor

import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.finishOperation
import ru.medals.domain.core.bussines.workers.initStatus
import ru.medals.domain.core.bussines.workers.operation
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.medals.domain.register.bussines.validate.*
import ru.medals.domain.register.bussines.workers.*
import ru.otus.cor.rootChain

class RegisterProcessor : IBaseProcessor<RegisterContext> {

	override suspend fun exec(ctx: RegisterContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain {
			initStatus("Инициализация статуса")

			operation("Регистрация владельца компаний", RegisterContext.Command.TEMP_REGISTER_OWNER) {
				validateUserEmailEmpty("Проверка, не пустой ли email")
				validateUserPasswordEmpty("Проверка, не пустой ли password")
				validateUserNameEmpty("Проверка, не пустой ли name")
				validateUserLoginEmpty("Проверка, не пустой ли login")
				trimFieldUser("Очищаем поля сотрудника")
				validateUserWithEmailExist("Проверка, есть ли сотрудник с таким email")
				validateUserWithLoginExist("Проверка, есть ли сотрудник с таким login")
				validateTempRegWithEmailExist("Не регистрируется ли сейчас владелец с таким email")
				sendCodeToUserEmailForRegister("Отправляем письмо с кодом на почту")
				saveTempRegUserToDb("Записываем данные в таблицу регистрации")
			}

			operation("Регистрация владельца компаний", RegisterContext.Command.VALID_REGISTER_OWNER) {
				validateUserEmailEmpty("Проверка, не пустой ли email")
				validateRegCodeEmpty("Проверка, не пустой ли код подтверждения")
				trimFieldCode("Очищаем код подтверждения")
				getTempRegByEmail("Получаем регистрационные данные")
				validateRegCode("Сопоставляем полученный код с кодом, высланным ранее на почту")
				createOwner("Создаем профиль владельца компаний в БД")
				generateTokens("Генерируем токены")
			}

			operation("Восстановление пароля по email", RegisterContext.Command.RESTORE_PASSWORD_EMAIL) {
				validateEmailEmpty("Проверка, не пустой ли email")
				trimFieldEmailAndCopyToValid("Очищаем email")
				getUserByEmailDb("Получаем сотрудника по email")
				validateTempRegRestorePswExist("Проверка, есть ли запись о сбросе пароля в БД (возможен ли повторный сброс)")
				sendLinkToUserEmailForRestore("Отправка письма со ссылкой")
				saveTempRegForResetPsw("Записываем данные для сброса пароля")
			}

			operation("Сброс пароля по email", RegisterContext.Command.RESET_PASSWORD) {
				validateUserIdEmpty("Проверка, не пустой ли userId")
				validateUserPasswordEmpty("Проверка, не пустой ли password")
				validateRegCodeEmpty("Проверка, не пустой ли код подтверждения")
				trimFieldCode("Очищаем код подтверждения")
				trimFieldUser("Очищаем поля сотрудника")
				validateTempRegCodeAndUserId("Проверка данных сброса на совпадение")
				validateUserWithIdNotExist("Проверка наличия сотрудника")
				resetUserPassword("Переустанавливаем пароль на новый")
			}

			finishOperation()
		}.build()
	}
}