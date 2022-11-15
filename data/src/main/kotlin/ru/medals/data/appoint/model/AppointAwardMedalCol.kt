package ru.medals.data.appoint.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.award.model.AwardMedalCol
import ru.medals.domain.appoint.model.AppointAwardMedal
import ru.medals.domain.appoint.model.AppointStatus
import java.util.*

data class AppointAwardMedalCol(
	val awardMedal: AwardMedalCol,
	val userId: String,
	val status: AppointStatus = AppointStatus.NONE,
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAppointAwardMedal() = AppointAwardMedal(
		awardMedal = awardMedal.toAwardMedal(),
		userId = userId,
		status = status,
		nomineeDate = nomineeDate,
		rewardDate = rewardDate,
		id = id
	)
}