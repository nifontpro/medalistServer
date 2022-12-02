package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import java.util.*

data class AwardCol(
	val name: String,
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Date? = null,
	val endDate: Date? = null,
	val score: Int? = null,
	val companyId: String,
	val relations: List<AwardRelate> = emptyList(),

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {
	fun toAward() = Award(
		name = name,
		description = description,
		criteria = criteria,
		startDate = startDate?.time,
		endDate = endDate?.time,
		score = score,
		companyId = companyId,
		relations = relations,

		imageUrl   = imageUrl,
		imageKey = imageKey,

		id = id
	)
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
