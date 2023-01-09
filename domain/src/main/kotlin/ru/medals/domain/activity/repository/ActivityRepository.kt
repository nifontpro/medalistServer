package ru.medals.domain.activity.repository

import ru.medals.domain.activity.model.Activity
import ru.medals.domain.activity.model.ActivityExt
import ru.medals.domain.activity.model.ActivityQuery
import ru.medals.domain.core.bussines.model.RepositoryData

interface ActivityRepository {

	suspend fun insert(activity: Activity): RepositoryData<Activity>
	suspend fun getByCompany(activityQuery: ActivityQuery): RepositoryData<List<ActivityExt>>
}