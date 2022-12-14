package ru.medals.domain.award.bussines.workers.message

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.model.MessageType
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AwardContext>.sendMessageToUser(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		val msg = buildString {
			append("Вы ")
			if (awardState == AwardState.NOMINEE) {
				append("номинированы на награду")
			} else {
				append("награждены наградой")
			}
			append(" \"${award.name}\"")
		}

		log.info("AWARD: $award")

		val message = Message(
			fromId = principalUser.id,
			toId = userIdValid,
			type = MessageType.SYSTEM,
			theme = "Награждение",
			themeSlug = "awardUser",
			text = msg,
			imageUrl = award.imageUrl
		)

		messageRepository.send(message)
	}
}
