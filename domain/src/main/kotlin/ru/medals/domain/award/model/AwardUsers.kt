package ru.medals.domain.award.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class AwardUsers(
	val name: String = "",
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Long? = null,
	val endDate: Long? = null,
	val state: AwardState = AwardState.NONE,
	val score: Int? = null,
	val companyId: String = "",
	val relateUsers: List<AwardRelateUser> = emptyList(),

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String = ""
) : IImages