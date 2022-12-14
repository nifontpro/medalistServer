package ru.medals.data.activity.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.award.model.AwardLiteCol
import ru.medals.data.user.model.UserLiteCol
import ru.medals.domain.activity.model.ActivityExt
import ru.medals.domain.activity.model.ActivityState

data class ActivityExtCol(
	val user: UserLiteCol? = null,
	val award: AwardLiteCol? = null,
	val companyId: String = "",

	val state: ActivityState = ActivityState.NONE,
	val date: Long = 0,
	val departmentName: String? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toActivityExt() = ActivityExt(
		user = user?.toUserLite(),
		award = award?.toAwardLite(),
		companyId = companyId,
		state = state,
		date = date,
		departmentName = departmentName,

		id = id
	)
}