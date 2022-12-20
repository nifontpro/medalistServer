package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.AwardLite
import java.util.*

data class AwardLiteCol(
	val name: String,
	val imageUrl: String? = null,
	val imageKey: String? = null,
	val startDate: Date? = null,
	val endDate: Date? = null,
	val companyId: String = "",

	@BsonId
	val id: String = ObjectId().toString()
) {

	fun toAwardLite() = AwardLite(
		name = name,
		imageUrl = imageUrl,
		imageKey = imageKey,
		state = calcAwardState(startDate, endDate),
		companyId = companyId,

		id = id
	)
}