package ru.medals.data.appoint.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.model.AppointStatus

data class AppointCol(
	val awardId: String? = null,
	val userId: String? = null,
	val status: AppointStatus? = null,
	val images: List<String>? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAppoint() = Appoint(
		awardId = awardId ?: "",
		userId = userId ?: "",
		status = status ?: AppointStatus.NONE,
		images = images ?: emptyList(),
		id = id
	)
}