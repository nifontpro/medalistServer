package ru.medals.ktor.medal

import io.ktor.server.application.*
import ru.medals.domain.core.response.IdResponse
import ru.medals.domain.core.response.baseResponseId
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.medals.domain.medal.bussines.processor.MedalProcessor
import ru.medals.domain.medal.model.Medal
import ru.medals.ktor.core.*
import ru.medals.ktor.medal.mappers.fromTransport
import ru.medals.ktor.medal.mappers.toTransportGetByCompany
import ru.medals.ktor.medal.mappers.toTransportGetById
import ru.medals.ktor.medal.model.request.*

suspend fun ApplicationCall.createMedal(processor: MedalProcessor) =
	authProcess<CreateMedalRequest, IdResponse, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { baseResponseId() }
	)

suspend fun ApplicationCall.deleteMedal(processor: MedalProcessor) =
	authProcess<DeleteMedalRequest, Unit, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.updateMedal(processor: MedalProcessor) =
	authProcess<UpdateMedalRequest, Unit, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.getMedalById(processor: MedalProcessor) =
	process<GetMedalByIdRequest, Medal, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetById() }
	)

suspend fun ApplicationCall.getMedalsByCompany(processor: MedalProcessor) =
	process<GetMedalsByCompanyRequest, List<Medal>, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetByCompany() }
	)

suspend fun ApplicationCall.getCountMedalsByCompany(processor: MedalProcessor) =
	process<GetCountMedalsByCompanyRequest, Long, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCount() }
	)

suspend fun ApplicationCall.updateMedalImageOld(processor: MedalProcessor) {
	val context = MedalContext().apply { command = MedalContext.Command.UPDATE_IMAGE_OLD }
	processImageSingle(context = context, processor = processor)
}

suspend fun ApplicationCall.createMedalImage(processor: MedalProcessor) {
	val context = MedalContext().apply { command = MedalContext.Command.IMAGE_ADD }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.updateMedalImage(processor: MedalProcessor) {
	val context = MedalContext().apply { command = MedalContext.Command.IMAGE_UPDATE }
	processImage(context = context, processor = processor)
}

suspend fun ApplicationCall.deleteMedalImage(processor: MedalProcessor) =
	authProcess<DeleteMedalImageRequest, Unit, MedalContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) }
	)