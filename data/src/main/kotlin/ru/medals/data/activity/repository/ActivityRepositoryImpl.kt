package ru.medals.data.activity.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.activity.model.ActivityCol
import ru.medals.data.activity.model.ActivityExtCol
import ru.medals.data.activity.model.toActivityColCreate
import ru.medals.data.activity.repository.ActivityRepoErrors.Companion.errorActivityCreate
import ru.medals.data.activity.repository.ActivityRepoErrors.Companion.errorGetActivity
import ru.medals.data.activity.repository.query.getActivityQuery
import ru.medals.domain.activity.model.Activity
import ru.medals.domain.activity.model.ActivityExt
import ru.medals.domain.activity.model.ActivityQuery
import ru.medals.domain.activity.repository.ActivityRepository
import ru.medals.domain.core.bussines.model.RepositoryData

class ActivityRepositoryImpl(
	db: CoroutineDatabase,
) : ActivityRepository {
	private val activities = db.getCollection<ActivityCol>()

	override suspend fun insert(activity: Activity): RepositoryData<Activity> {
		val activityCol = activity.toActivityColCreate()
		return try {
			activities.insertOne(activityCol)
			RepositoryData.success(data = activityCol.toActivity())
		} catch (e: Exception) {
			errorActivityCreate()
		}
	}

	override suspend fun getByCompany(activityQuery: ActivityQuery): RepositoryData<List<ActivityExt>> {
		return try {
			val activityList = activities.aggregate<ActivityExtCol>(
				getActivityQuery(activityQuery)
			).toList().map { it.toActivityExt() }
			RepositoryData.success(data = activityList)
		} catch (e: Exception) {
			errorGetActivity()
		}
	}

}
