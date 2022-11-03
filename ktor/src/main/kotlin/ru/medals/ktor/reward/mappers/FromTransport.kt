package ru.medals.ktor.reward.mappers

import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.ktor.reward.model.request.*

fun RewardContext.fromTransport(request: NomineeRequest) {
	this.command = RewardContext.Command.NOMINEE_USER
	nominee = request.toNominee()
}

fun RewardContext.fromTransport(request: PutSignatureRequest) {
	this.command = RewardContext.Command.PUT_SIGNATURE
	rewardId = request.rewardId
}

fun RewardContext.fromTransport(request: RewardUserRequest) {
	this.command = RewardContext.Command.REWARD_USER
	rewardId = request.rewardId
}

fun RewardContext.fromTransport(request: GetUserRewardsRequest) {
	this.command = RewardContext.Command.GET_USER_REWARDS
	userId = request.userId
}

fun RewardContext.fromTransport(request: GetRewardInfoRequest) {
	this.command = RewardContext.Command.GET_REWARD_INFO
	rewardId = request.rewardId
}

fun RewardContext.fromTransport(request: GetRewardByIdRequest) {
	this.command = RewardContext.Command.GET_REWARD_BY_ID
	rewardId = request.rewardId
}

fun RewardContext.fromTransport(request: GetRewardCountRequest) {
	this.command = RewardContext.Command.GET_REWARD_COUNT
	companyId = request.companyId
}