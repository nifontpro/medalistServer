package ru.medals.data.user.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.award.model.AwardLiteCol
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.user.model.Gender
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserAwardsLite

/**
 * Класс сотрудников для вывода со списком наград
 * Включает только необходимые поля
 */
data class UserAwardsLiteCol(
	val email: String? = null,
	val login: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val hashPassword: String? = null,
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

	val awards: List<AwardLiteCol> = emptyList(),

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {

	fun toUserAwards() = UserAwardsLite(
		id = id,
		email = email,
		login = login,
		name = name,
		patronymic = patronymic,
		lastname = lastname,
		role = role ?: User.NONE,
		post = post,
		phone = phone,
		gender = gender ?: Gender.UNDEFINED,
		companyId = companyId,
		departmentId = departmentId,
		departmentName = departmentName,
		score = score,
		currentScore = currentScore,
		awardCount = awardCount,
		awards = awards.map { it.toAwardLite() },

		sysImage = sysImage,
		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,
	)
}