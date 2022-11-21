package ru.medals.domain.user.model

import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class UserMedals(
	val email: String? = null,
	val login: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val hashPassword: String? = null,
	val role: String? = null,
	val bio: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val score: Int? = null,
	val currentScore: Int? = null,
	val rewardCount: Int? = null,
	val mnc: Boolean? = null, // Является ли членом номинационной коммисии

	val medalsInfo: List<MedalInfo> = emptyList(),

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	val id: String
) : IImages