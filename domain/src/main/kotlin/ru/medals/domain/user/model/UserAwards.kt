package ru.medals.domain.user.model

import ru.medals.domain.award.model.AwardLite
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class UserAwards(
	val email: String? = null,
	val login: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val role: String? = null,
	val post: String? = null,
	val phone: String? = null,
	val gender: Gender? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val score: Int? = null,
	val currentScore: Int? = null,
	val awardCount: Int = 0,
	val departmentName: String? = null,

	val awards: List<AwardLite> = emptyList(),

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String = ""
) : IImages