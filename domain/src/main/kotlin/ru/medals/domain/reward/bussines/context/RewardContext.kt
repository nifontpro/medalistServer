package ru.medals.domain.reward.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.medal.repository.MedalRepository
import ru.medals.domain.reward.model.Nominee
import ru.medals.domain.reward.model.Reward
import ru.medals.domain.reward.model.RewardInfo
import ru.medals.domain.reward.repository.RewardRepository
import ru.medals.domain.user.repository.UserRepository

data class RewardContext(
	var rewardId: String = "",
	var reward: Reward = Reward(),
	var nominee: Nominee = Nominee(),

	var rewards: List<Reward> = emptyList(),
	var rewardInfo: RewardInfo? = null,

	) : BaseContext(command = Command.NONE) {

	val rewardRepository: RewardRepository by inject(RewardRepository::class.java)
	val userRepository: UserRepository by inject(UserRepository::class.java)
	val medalRepository: MedalRepository by inject(MedalRepository::class.java)

	enum class Command : IBaseCommand {
		NONE,
		REWARD_USER,
		NOMINEE_USER,
		GET_USER_REWARDS,
		GET_REWARD_INFO,
		GET_REWARD_BY_ID,
		GET_REWARD_COUNT,
		PUT_SIGNATURE,
	}
}