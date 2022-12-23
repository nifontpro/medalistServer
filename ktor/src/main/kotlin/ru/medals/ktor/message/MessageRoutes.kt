package ru.medals.ktor.message

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.medals.domain.message.bussines.processor.MessageProcessor
import ru.medals.domain.user.model.User.Companion.USER

fun Route.messageRoutes() {

	val messageProcessor: MessageProcessor by inject()

	route("message") {

		authenticate(USER) {

			/**
			 * Отправить сообщение
			 */
			post("send") {
				call.sendMessage(messageProcessor)
			}

			/**
			 * Получить сообщения сотрудника
			 */
			post("user") {
				call.getMessageByUser(messageProcessor)
			}

			/**
			 * Пометить сообщение как прочитанное
			 */
			post("read") {
				call.markMessageAsRead(messageProcessor)
			}

			/**
			 * Пометить сообщение как непрочитанное
			 */
			post("unread") {
				call.markMessageAsUnread(messageProcessor)
			}

		}

	}
}
