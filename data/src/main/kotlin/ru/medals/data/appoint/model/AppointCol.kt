package ru.medals.data.appoint.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.model.AppointStatus
import java.util.*

data class AppointCol(
	val awardId: String? = null,
	val userId: String? = null,
	val status: AppointStatus? = null,
	val images: List<String>? = null,
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAppoint() = Appoint(
		awardId = awardId ?: "",
		userId = userId ?: "",
		status = status ?: AppointStatus.NONE,
		images = images ?: emptyList(),
		nomineeDate = nomineeDate,
		rewardDate = rewardDate,
		id = id
	)
}

fun Appoint.toAppointCol(create: Boolean = false) = AppointCol(
	awardId = awardId,
	userId = userId,
	status = status,
	images = images,
	nomineeDate = nomineeDate,
	rewardDate = rewardDate,
	id =  if (create) ObjectId().toString() else id
)