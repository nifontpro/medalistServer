package ru.medals.ktor.award

import io.ktor.server.application.*
import ru.medals.domain.award.bussines.context.AwardCommand
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.bussines.processor.AwardProcessor
import ru.medals.domain.award.model.*
import ru.medals.ktor.award.mappers.*
import ru.medals.ktor.award.model.request.*
import ru.medals.ktor.core.authProcess
import ru.medals.ktor.core.process
import ru.medals.ktor.core.processImageSingle
import ru.medals.ktor.core.toTransportGetIds

suspend fun ApplicationCall.createAward(processor: AwardProcessor) =
	authProcess<CreateAwardRequest, Award, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAward() }
	)

suspend fun ApplicationCall.deleteAward(processor: AwardProcessor) =
	authProcess<DeleteAwardRequest, Award, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAward() }
	)

suspend fun ApplicationCall.getAwardsByCompany(processor: AwardProcessor) =
	process<GetAwardsByCompanyRequest, List<Award>, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwards() }
	)

suspend fun ApplicationCall.getAwardsByCompanyWithUsers(processor: AwardProcessor) =
	process<GetAllAwardsByCompanyRequest, List<AwardUsers>, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardsUsers() }
	)

suspend fun ApplicationCall.getAwardById(processor: AwardProcessor) =
	process<GetAwardByIdRequest, Award, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAward() }
	)

suspend fun ApplicationCall.getAwardByIdWithUsers(processor: AwardProcessor) =
	process<GetAwardByIdUsersRequest, AwardUsers, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardUsers() }
	)

suspend fun ApplicationCall.updateAward(processor: AwardProcessor) =
	authProcess<UpdateAwardRequest, Unit, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.awardUser(processor: AwardProcessor) =
	authProcess<AwardUserRequest, Unit, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.awardUserDelete(processor: AwardProcessor) =
	authProcess<AwardUserDeleteRequest, AwardRelate, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardRelate() }
	)

suspend fun ApplicationCall.getAwardCountByCompany(processor: AwardProcessor) =
	process<GetAwardCountByCompanyRequest, AwardCount, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardCount() }
	)

suspend fun ApplicationCall.updateAwardImageOld(processor: AwardProcessor) {
	val context = AwardContext().apply { command = AwardCommand.UPDATE_IMAGE_OLD }
	processImageSingle(context = context, processor = processor)
}

suspend fun ApplicationCall.deleteAwardImageOld(processor: AwardProcessor) =
	authProcess<AwardDeleteMainImageRequest, Unit, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)

suspend fun ApplicationCall.setAwardImageGallery(processor: AwardProcessor) =
	authProcess<SetAwardGalleryImageRequest, AwardLite, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardLite() }
	)

suspend fun ApplicationCall.getAwardIds(processor: AwardProcessor) =
	process<GetIdsRequest, List<String>, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetIds() }
	)