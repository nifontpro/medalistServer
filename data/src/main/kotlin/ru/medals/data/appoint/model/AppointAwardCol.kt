package ru.medals.data.appoint.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.award.model.AwardCol
import ru.medals.domain.appoint.model.AppointAward
import ru.medals.domain.appoint.model.AppointStatus
import java.util.*

data class AppointAwardCol(
	val award: AwardCol,
	val userId: String,
	val status: AppointStatus = AppointStatus.NONE,
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAppointAward() = AppointAward(
		award = award.toAward(),
		userId = userId,
		status = status,
		nomineeDate = nomineeDate,
		rewardDate = rewardDate,
		id = id
	)
}