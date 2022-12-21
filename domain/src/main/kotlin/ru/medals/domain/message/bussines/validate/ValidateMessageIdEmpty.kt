package ru.medals.domain.message.bussines.validate

import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.message.bussines.context.MessageContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<MessageContext>.validateMessageIdEmpty(title: String) = worker {
	this.title = title
	on { messageId.isBlank() }
	handle {
		fail(
			errorValidation(
				field = "messageId",
				violationCode = "empty",
				description = "messageId не должно быть пустым"
			)
		)
	}
}
