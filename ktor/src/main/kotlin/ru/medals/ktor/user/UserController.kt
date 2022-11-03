package ru.medals.ktor.user

import io.ktor.server.application.*
import ru.medals.domain.core.response.IdResponse
import ru.medals.domain.core.response.baseResponseId
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.bussines.processor.UserProcessor
import ru.medals.domain.user.model.User
import ru.medals.ktor.core.*
import ru.medals.ktor.user.mappers.fromTransport
import ru.medals.ktor.user.mappers.toTransportGetById
import ru.medals.ktor.user.mappers.toTransportGetUsers
import ru.medals.ktor.user.model.request.*

suspend fun ApplicationCall.createUser(processor: UserProcessor) =
	authProcess<CreateUserRequest, IdResponse, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { baseResponseId() }
	)

suspend fun ApplicationCall.getUserById(processor: UserProcessor) =
	process<GetUserByIdRequest, User, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetById() }
	)

suspend fun ApplicationCall.getUsersByDepartment(processor: UserProcessor) =
	process<GetUsersByDepartmentRequest, List<User>, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetUsers() }
	)

suspend fun ApplicationCall.getBestUsersByCompany(processor: UserProcessor) =
	process<GetBestUsersByCompanyRequest, List<User>, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetUsers() }
	)

suspend fun ApplicationCall.getBosses(processor: UserProcessor) =
	authProcess<GetBossesRequest, List<User>, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetUsers() }
	)

suspend fun ApplicationCall.deleteUser(processor: UserProcessor) =
	authProcess<DeleteUserRequest, Unit, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) }
	)

suspend fun ApplicationCall.updateUser(processor: UserProcessor) =
	authProcess<UpdateUserRequest, Unit, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) }
	)

suspend fun ApplicationCall.getUserCountByCompany(processor: UserProcessor) =
	process<UserCountByCompanyRequest, Long, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCount() }
	)

suspend fun ApplicationCall.getUserCountByDepartment(processor: UserProcessor) =
	process<UserCountByDepartmentRequest, Long, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCount() }
	)

suspend fun ApplicationCall.updateUserImageOld(processor: UserProcessor) {
	val context = UserContext().apply { command = UserContext.Command.UPDATE_IMAGE }
	processImageSingle(context = context, processor = processor)
}

suspend fun ApplicationCall.createUserImage(processor: UserProcessor) {
	val context = UserContext().apply { command = UserContext.Command.IMAGE_ADD }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.updateUserImage(processor: UserProcessor) {
	val context = UserContext().apply { command = UserContext.Command.IMAGE_UPDATE }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.deleteUserImage(processor: UserProcessor) =
	authProcess<DeleteUserImageRequest, Unit, UserContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) }
	)