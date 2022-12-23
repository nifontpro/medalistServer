package ru.medals.domain.message.bussines.context

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.message.model.Message

data class MessageContext(

	var message: Message = Message(),
	var messages: List<Message> = emptyList(),

	var messageId: String = "",

	) : BaseContext(command = MessageCommand.NONE) {

}

@Suppress("unused")
enum class MessageCommand : IBaseCommand {
	NONE,
	SEND,
	DELETE,
	GET_BY_USER,
	MARK_READ,
	MARK_UNREAD
}