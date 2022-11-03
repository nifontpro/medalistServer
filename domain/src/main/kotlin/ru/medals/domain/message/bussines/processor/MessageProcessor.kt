package ru.medals.domain.message.bussines.processor

import ru.medals.domain.company.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.workers.validation.*
import ru.medals.domain.message.bussines.context.MessageContext
import ru.medals.domain.message.bussines.workers.sendMessage
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class MessageProcessor : IBaseProcessor<MessageContext> {

	override suspend fun exec(ctx: MessageContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<MessageContext> {
			initStatus("Инициализация статуса")

			operation("Отправить сообщение", MessageContext.Command.SEND) {
				sendMessage("Отправка сообщения в БД")
			}

			finishOperation()
		}.build()
	}
}