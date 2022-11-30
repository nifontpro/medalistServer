package ru.medals.domain.award.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.award.model.AwardUsers
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.user.repository.UserRepository

data class AwardContext(

	var award: Award = Award(),
	var awardUsers: AwardUsers = AwardUsers(),
	var awards: List<Award> = emptyList(),
	var awardsUsers: List<AwardUsers> = emptyList(),
	var awardRelate: AwardRelate? = null,
	var awardRelateValid: AwardRelate = AwardRelate(),

	var awardId: String = "",
	var awardState: AwardState = AwardState.NONE,
	var isNew: Boolean = true,

	) : BaseContext(command = AwardCommand.NONE) {

	val awardRepository: AwardRepository by inject(AwardRepository::class.java)
	val userRepository: UserRepository by inject(UserRepository::class.java)
}

enum class AwardCommand : IBaseCommand {
	NONE,
	CREATE,
	DELETE,
	GET_BY_ID,
	GET_BY_ID_WITH_USERS,
	GET_BY_COMPANY,
	GET_BY_COMPANY_WITH_USERS,
	UPDATE,
	UPDATE_IMAGE_OLD,
	AWARD_USER,
	AWARD_USER_DELETE
}