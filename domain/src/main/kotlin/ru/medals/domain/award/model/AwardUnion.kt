package ru.medals.domain.award.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class AwardUnion(
	val name: String,
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Long? = null,
	val endDate: Long? = null,
	val awardState: AwardState = AwardState.NONE,
	val score: Int? = null,
	val companyId: String,

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	// From user relate:
	val userId: String = "",
	val userState: AwardState = AwardState.NONE,
	val nomineeDate: Long? = null,
	val awardDate: Long? = null,
	val nomineeUserId: String? = null,
	val awardUserId: String? = null,

	val id: String = ""
) : IImages