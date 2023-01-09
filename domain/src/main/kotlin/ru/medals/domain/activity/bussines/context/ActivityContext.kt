package ru.medals.domain.activity.bussines.context

import mu.KLogger
import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.activity.model.ActivityExt
import ru.medals.domain.activity.model.ActivityQuery
import ru.medals.domain.activity.repository.ActivityRepository
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand

data class ActivityContext(

	var activityQuery: ActivityQuery = ActivityQuery(),
	var activities: List<ActivityExt> = emptyList(),
	val log: KLogger = KotlinLogging.logger {}

) : BaseContext(command = ActivityCommand.NONE) {
	val activityRepository: ActivityRepository by inject(ActivityRepository::class.java)
}

enum class ActivityCommand : IBaseCommand {
	NONE,
	GET_BY_COMPANY
}