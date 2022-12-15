package ru.medals.domain.user.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserAwardCount
import ru.medals.domain.user.model.UserAwardsLite
import ru.medals.domain.user.model.UserAwardsUnion
import ru.medals.domain.user.model.count.UserAwardsCountDep
import ru.medals.domain.user.repository.UserRepository

data class UserContext(

	var user: User = User(),
	var userAwards: UserAwardsUnion = UserAwardsUnion(),

	val users: MutableList<User> = mutableListOf(),
	var usersAwardsLite: List<UserAwardsLite> = emptyList(),
	var usersAwardsUnion: List<UserAwardsUnion> = emptyList(),
	var userAwardCount: UserAwardCount = UserAwardCount(),
	var usersAwardsCountDep: List<UserAwardsCountDep> = emptyList(),

	) : BaseContext(command = UserCommand.NONE) {

	val userRepository: UserRepository by inject(UserRepository::class.java)
	val companyRepository: CompanyRepository by inject(CompanyRepository::class.java)
	val awardRepository: AwardRepository by inject(AwardRepository::class.java)
}

enum class UserCommand : IBaseCommand {
	NONE,
	CREATE,
	GET_BY_ID,
	GET_BY_ID_DEP_NAME,
	GET_BY_ID_AWARDS,
	GET_BY_DEPARTMENT,
	GET_BY_COMPANY,
	GET_BY_COMPANY_DEP_NAME,
	GET_WITH_AWARDS,
	GET_WITH_AWARDS_FULL,
	GET_BOSSES,
	GET_BEST,
	UPDATE,
	UPDATE_IMAGE,
	DELETE,
	COUNT_BY_COMPANY,
	COUNT_BY_DEPARTMENT,
	AWARD_COUNT_BY_DEPARTMENT,
	AWARD_COUNT_BY_COMPANY,
	AWARD_COUNT_BY_COMPANY_AGR_DEP,
	IMAGE_ADD,
	IMAGE_UPDATE,
	IMAGE_DELETE
}