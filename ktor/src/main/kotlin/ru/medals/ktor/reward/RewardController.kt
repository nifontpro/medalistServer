package ru.medals.ktor.reward

import io.ktor.server.application.*
import ru.medals.domain.core.response.IdResponse
import ru.medals.domain.core.response.baseResponseId
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.bussines.processor.RewardProcessor
import ru.medals.domain.reward.model.Reward
import ru.medals.domain.reward.model.RewardInfo
import ru.medals.ktor.core.authProcess
import ru.medals.ktor.core.process
import ru.medals.ktor.core.toTransportGetCount
import ru.medals.ktor.reward.mappers.fromTransport
import ru.medals.ktor.reward.mappers.toTransportGetById
import ru.medals.ktor.reward.mappers.toTransportRewardInfo
import ru.medals.ktor.reward.mappers.toTransportUserRewards
import ru.medals.ktor.reward.model.request.*

suspend fun ApplicationCall.rewardUser(processor: RewardProcessor) =
	authProcess<RewardUserRequest, Unit, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { null }
	)

suspend fun ApplicationCall.nomineeUser(processor: RewardProcessor) =
	authProcess<NomineeRequest, IdResponse, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { baseResponseId() }
	)

suspend fun ApplicationCall.getUserRewards(processor: RewardProcessor) =
	process<GetUserRewardsRequest, List<Reward>, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportUserRewards() }
	)

suspend fun ApplicationCall.getRewardInfo(processor: RewardProcessor) =
	process<GetRewardInfoRequest, RewardInfo, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportRewardInfo() }
	)

suspend fun ApplicationCall.getRewardById(processor: RewardProcessor) =
	process<GetRewardByIdRequest, Reward, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetById() }
	)

suspend fun ApplicationCall.getRewardCountByCompany(processor: RewardProcessor) =
	process<GetRewardCountRequest, Long, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetCount() }
	)

suspend fun ApplicationCall.putSignature(processor: RewardProcessor) =
	authProcess<PutSignatureRequest, Unit, RewardContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { null }
	)