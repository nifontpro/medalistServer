package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.medal.model.MedalCol
import ru.medals.domain.award.model.AwardMedal
import ru.medals.domain.award.model.AwardStatus
import java.util.*

data class AwardMedalCol(
	val name: String? = null,
	val description: String? = null,
	val createDate: Date? = null,
	val companyId: String? = null,
	val medals: List<MedalCol> = emptyList(),
	val criteria: String? = null,
	val status: AwardStatus? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toAwardMedal() = AwardMedal(
		name = name,
		description = description,
		createDate = createDate?.time ?: -1,
		companyId = companyId,
		medal = medals.firstOrNull()?.toMedal(),
		criteria = criteria,
		status = status ?: AwardStatus.NONE,
		id = id
	)
}