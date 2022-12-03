package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.AwardLite

data class AwardLiteCol(
	val name: String,
	val imageUrl: String? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {

	fun toAwardLite() = AwardLite(
		name = name,
		imageUrl = imageUrl,
		id = id
	)
}