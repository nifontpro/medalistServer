package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.AwardLite
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import java.util.*

data class AwardUnionCol(
	val name: String,
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Date? = null,
	val endDate: Date? = null,
	val score: Int? = null,
	val companyId: String,

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	// From user relate:
	val userId: String = "",
	val state: AwardState = AwardState.NONE,
	val nomineeDate: Long? = null,
	val awardDate: Long? = null,
	val nomineeUserId: String? = null,
	val awardUserId: String? = null,

	@BsonId
	val id: String = ObjectId().toString()
): IImages {

	fun toAwardLite() = AwardLite(
		name = name,
		imageUrl = imageUrl,
		id = id
	)
}