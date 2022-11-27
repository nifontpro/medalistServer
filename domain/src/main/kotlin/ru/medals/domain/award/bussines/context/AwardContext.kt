package ru.medals.domain.award.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardUsers
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand

data class AwardContext(

	var award: Award = Award(), // set
	var awardUsers: AwardUsers = AwardUsers(),
	var awards: List<Award> = emptyList(),
	var awardsUsers: List<AwardUsers> = emptyList(),

	var awardId: String = "",

	) : BaseContext(command = AwardCommand.NONE) {

	val awardRepository: AwardRepository by inject(AwardRepository::class.java)
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
	UPDATE_IMAGE_OLD
}