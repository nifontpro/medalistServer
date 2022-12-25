package ru.medals.domain.award.bussines.workers.message

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.model.MessageType
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.sendMessageToPrincipal(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		val msg = buildString {
			append("Вами ")
			if (awardState == AwardState.NOMINEE) {
				append("номинирован ")
			} else {
				append("награжден ")
			}
			append(userFIO) // кого наградили
			append(" наградой \"${award.name}\" ")
		}

		val message = Message(
			fromId = null,
			toId = principalUser.id,
			type = MessageType.SYSTEM,
			theme = "Награждение",
			themeSlug = "awardUser",
			text = msg,
			imageUrl = award.imageUrl
		)

		messageRepository.send(message)
	}
}
