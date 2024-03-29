package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import java.util.*

//private val log = KotlinLogging.logger {}

data class AwardCol(
	val name: String = "",
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Date? = null,
	val endDate: Date? = null,
	val score: Int? = null,
	val companyId: String = "",
	val relations: List<AwardRelate> = emptyList(),

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {
	fun toAward(): Award {

		return Award(
			name = name,
			description = description,
			criteria = criteria,
			startDate = startDate?.time,
			endDate = endDate?.time,
			state = calcAwardState(startDate, endDate),
			score = score,
			companyId = companyId,
			relations = relations,

			sysImage = sysImage,
			imageUrl = imageUrl,
			imageKey = imageKey,

			id = id
		)
	}
}

fun Award.toAwardColCreate() = AwardCol(
	name = name,
	description = description,
	criteria = criteria,
	startDate = startDate?.let { Date(it) },
	endDate = endDate?.let { Date(it) },
	companyId = companyId,
	score = score,
	id = ObjectId().toString()
)

/**
 * Определение состояния награды в зависимости от времени
 */
fun calcAwardState(startDate: Date?, endDate: Date?): AwardState {
	val startTime = startDate?.time
	val endTime = endDate?.time
	val now = System.currentTimeMillis()
	val awardState = if (endTime != null && startTime != null) {
		when {
			now >= startTime && now < endTime -> AwardState.NOMINEE
			now >= endTime -> AwardState.AWARD
			else -> AwardState.NONE
		}
	} else {
		AwardState.NONE
	}
	return awardState
}
