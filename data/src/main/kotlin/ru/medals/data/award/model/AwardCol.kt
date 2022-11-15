package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardStatus
import java.util.*

data class AwardCol(
	val name: String? = null,
	val description: String? = null,
	val nomineeDate: Date? = null,
	val rewardDate: Date? = null,
	val companyId: String? = null,
	val medalId: String? = null,
	val criteria: String? = null,
	val status: AwardStatus? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAward() = Award(
		name = name,
		description = description,
		nomineeDate = nomineeDate?.time,
		rewardDate = rewardDate?.time,
		companyId = companyId,
		medalId = medalId,
		criteria = criteria,
		status = status ?: AwardStatus.NONE,
		id = id
	)
}

fun Award.toAwardCol(create: Boolean = false) = AwardCol(
	name = name,
	description = description,
	nomineeDate = if (create) Date() else null,
	companyId = companyId,
	medalId = medalId,
	criteria = criteria,
	status = status,
	id = if (create) ObjectId().toString() else id
)
