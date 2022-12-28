package ru.medals.data.activity.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.activity.model.ActivityCol
import ru.medals.data.activity.model.ActivityExtCol
import ru.medals.data.activity.model.toActivityColCreate
import ru.medals.data.activity.repository.ActivityRepoErrors.Companion.errorActivityCreate
import ru.medals.data.activity.repository.query.getActivityQuery
import ru.medals.domain.activity.model.Activity
import ru.medals.domain.activity.repository.ActivityRepository
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.util.separator

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

	override suspend fun getByCompany(
		companyId: String,
		startDate: Long,
		endDate: Long,
		filter: String?
	) {
		val res = activities.aggregate<ActivityExtCol>(
			getActivityQuery(
				companyId = companyId,
				startDate = startDate,
				endDate = endDate,
				filter = filter
			)
		).toList().map { it.toActivityExt() }
		separator()
		println(res)
	}

}
