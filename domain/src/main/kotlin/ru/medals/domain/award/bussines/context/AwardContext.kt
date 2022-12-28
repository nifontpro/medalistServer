package ru.medals.domain.award.bussines.context

import mu.KLogger
import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.activity.repository.ActivityRepository
import ru.medals.domain.award.model.*
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand

data class AwardContext(

	var award: Award = Award(),
	var awardLite: AwardLite = AwardLite(),
	var awardUsers: AwardUsers = AwardUsers(),
	var awards: List<Award> = emptyList(),
	var awardsUsers: List<AwardUsers> = emptyList(),
	var awardRelate: AwardRelate? = null,
	var awardRelateValid: AwardRelate = AwardRelate(),
	var awardCount: AwardCount = AwardCount(),

	var awardId: String = "",
	var awardState: AwardState = AwardState.NONE,
	var isNew: Boolean = true,

	val log: KLogger = KotlinLogging.logger {}

) : BaseContext(command = AwardCommand.NONE) {

	val awardRepository: AwardRepository by inject(AwardRepository::class.java)
	val activityRepository: ActivityRepository by inject(ActivityRepository::class.java)
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
	DELETE_IMAGE_OLD,
	AWARD_USER,
	AWARD_USER_DELETE,
	GET_COUNT
}