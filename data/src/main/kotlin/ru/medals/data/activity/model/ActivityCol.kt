package ru.medals.data.activity.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.activity.model.Activity
import ru.medals.domain.activity.model.ActivityState

data class ActivityCol(
	val userId: String = "",
	val awardId: String = "",
	val companyId: String? = null,

	val state: ActivityState = ActivityState.NONE,
	val date: Long = 0,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toActivity() = Activity(
		userId = userId,
		awardId = awardId,
		companyId = companyId,
		state = state,
		date = date,
		id = id
	)
}

fun Activity.toActivityColCreate() = ActivityCol(
	userId = userId,
	awardId = awardId,
	companyId = companyId,
	state = state,
	date = date,
)
