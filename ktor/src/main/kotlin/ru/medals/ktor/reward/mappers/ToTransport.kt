package ru.medals.ktor.reward.mappers

import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.model.Reward
import ru.medals.domain.reward.model.RewardInfo

fun RewardContext.toTransportUserRewards(): List<Reward> = rewards

fun RewardContext.toTransportRewardInfo(): RewardInfo? = rewardInfo

fun RewardContext.toTransportGetById(): Reward = reward
