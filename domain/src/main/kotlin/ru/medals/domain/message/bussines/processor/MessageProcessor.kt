package ru.medals.domain.message.bussines.processor

import ru.medals.domain.company.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.workers.validation.*
import ru.medals.domain.message.bussines.context.MessageCommand
import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.bussines.validate.validateMessageIdEmpty
import ru.medals.domain.message.bussines.workers.*
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class MessageProcessor : IBaseProcessor<MessageContext> {

	override suspend fun exec(ctx: MessageContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<MessageContext> {
			initStatus("Инициализация статуса")

			operation("Отправить сообщение", MessageCommand.SEND) {
				sendMessage("Отправка сообщения в БД")
			}

			operation("Получить сообщения сотрудника", MessageCommand.GET_BY_USER) {
				validateUserIdEmpty("Проверка userId")
				trimFieldUserIdAndCopyToValid("Очищаем")
				validateUserLevel("Доступ к сообщениям - сотрудник и выше")
//				validateThisUserLevel("Доступ - только данному сотруднику")
				getMessageByUserDb("Получаем сообщения сотрудника")
			}

			operation("Пометить сообщение как прочитаное", MessageCommand.MARK_READ) {
				validateMessageIdEmpty("Проверяем messageId")
				trimFieldMessageId("Очищаем")
				getMessageByIdDb("Получаем сообщение сотрудника")
				validateUserLevel("Доступ к сообщениям - сотрудник и выше")
				markMessageReadDb("Помечаем сообщение как прочитанное")
			}

			finishOperation()
		}.build()
	}
}