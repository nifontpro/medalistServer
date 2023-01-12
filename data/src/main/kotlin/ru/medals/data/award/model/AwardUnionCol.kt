package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.award.model.AwardUnion
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

	override val sysImage: Boolean = false,
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
) : IImages {

	fun toAwardUnion() = AwardUnion(
		name = name,
		description = description,
		criteria = criteria,
		startDate = startDate?.time,
		endDate = endDate?.time,
		awardState = calcAwardState(startDate, endDate),
		score = score,
		companyId = companyId,

		sysImage = sysImage,
		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,

		// from user relate
		userId = userId,
		userState = state,
		nomineeDate = nomineeDate,
		awardDate = awardDate,
		nomineeUserId = nomineeUserId,
		awardUserId = awardUserId,

		id = id
	)
}