package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.AwardLite
import java.util.*

data class AwardLiteCol(
	val name: String,
	val imageUrl: String? = null,
	val startDate: Date? = null,
	val endDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {

	fun toAwardLite() = AwardLite(
		name = name,
		imageUrl = imageUrl,
		state = calcAwardState(startDate, endDate),

		id = id
	)
}