package ru.medals.ktor.award

import io.ktor.server.application.*
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.bussines.processor.AwardProcessor
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardMedal
import ru.medals.ktor.award.mappers.fromTransport
import ru.medals.ktor.award.mappers.toTransportGetAward
import ru.medals.ktor.award.mappers.toTransportGetAwardMedal
import ru.medals.ktor.award.mappers.toTransportGetAwards
import ru.medals.ktor.award.model.request.*
import ru.medals.ktor.core.authProcess
import ru.medals.ktor.core.process

suspend fun ApplicationCall.createAward(processor: AwardProcessor) =
	authProcess<CreateAwardRequest, Award, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAward() }
	)

suspend fun ApplicationCall.deleteAward(processor: AwardProcessor) =
	authProcess<DeleteAwardRequest, AwardMedal, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardMedal() }
	)

suspend fun ApplicationCall.getAwardsByCompany(processor: AwardProcessor) =
	process<GetAwardsByCompanyRequest, List<AwardMedal>, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwards() }
	)

suspend fun ApplicationCall.getAwardById(processor: AwardProcessor) =
	process<GetAwardByIdRequest, AwardMedal, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetAwardMedal() }
	)

suspend fun ApplicationCall.updateAward(processor: AwardProcessor) =
	authProcess<UpdateAwardRequest, Unit, AwardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
	)