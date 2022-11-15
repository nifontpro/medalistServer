package ru.medals.data.appoint.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.user.model.UserCol
import ru.medals.domain.appoint.model.AppointStatus
import ru.medals.domain.appoint.model.AppointUser
import java.util.*

data class AppointUserCol(
	val awardId: String,
	val user: UserCol,
	val status: AppointStatus = AppointStatus.NONE,
	val images: List<String> = emptyList(),
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAppointUser() = AppointUser(
		awardId = awardId,
		user = user.toUser(),
		status = status,
		images = images,
		nomineeDate = nomineeDate?.time,
		rewardDate = rewardDate?.time,
		id = id
	)
}