package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.Award
import java.util.*

data class AwardCol(
	val createDate: Date? = null,
	val medalId: String? = null,
	val criteria: String? = null,
	val description: String? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAward() = Award(
		createDate = createDate?.time ?: -1,
		medalId = medalId ?: "",
		criteria = criteria,
		description = description,
		id = id
	)
}

fun Award.toAwardCol() = AwardCol(
	createDate = Date(),
	medalId = medalId,
	criteria = criteria,
	description = description
)
