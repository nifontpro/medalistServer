package ru.medals.data.appoint.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.model.AppointStatus
import java.util.*

data class AppointCol(
	val awardId: String,
	val userId: String,
	val status: AppointStatus = AppointStatus.NONE,
	val images: List<String> = emptyList(),
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAppoint() = Appoint(
		awardId = awardId,
		userId = userId,
		status = status,
		images = images,
		nomineeDate = nomineeDate?.time,
		rewardDate = rewardDate?.time,
		id = id
	)
}