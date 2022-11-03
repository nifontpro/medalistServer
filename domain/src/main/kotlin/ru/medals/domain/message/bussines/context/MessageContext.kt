package ru.medals.domain.message.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.message.model.Message
import ru.medals.domain.message.repository.MessageRepository

data class MessageContext(

	var message: Message = Message(),
	var messages: List<Message> = emptyList(),

	) : BaseContext(command = Command.NONE) {

	val messageRepository: MessageRepository by inject(MessageRepository::class.java)

	enum class Command : IBaseCommand {
		NONE,
		SEND,
		DELETE,
		GET_BY_USER,
		MARK_READ
	}
}