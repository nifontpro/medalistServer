package ru.medals.data.user.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.user.model.Gender
import ru.medals.domain.user.model.User

data class UserCol(
	val email: String? = null,
	val login: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val hashPassword: String? = null,
	val role: String? = null,
	val bio: String? = null,
	val post: String? = null,
	val phone: String? = null,
	val gender: Gender? = null,
	val description: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val score: Int? = null,
	val currentScore: Int? = null,
	val awardCount: Int = 0,
	val mnc: Boolean? = null, // Является ли членом номинационной коммисии
	val departmentName: String? = null,

	val medalsInfo: List<MedalInfoCol> = emptyList(),

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {

	fun toUser(clearHashPassword: Boolean = true): User {
		return User(
			id = id,
			email = email,
			login = login,
			name = name,
			patronymic = patronymic,
			lastname = lastname,
			hashPassword = if (clearHashPassword) null else hashPassword,
			role = role ?: User.NONE,
			bio = bio,
			post = post,
			phone = phone,
			gender = gender ?: Gender.UNDEFINED,
			description = description,
			companyId = companyId,
			departmentId = departmentId,
			departmentName = departmentName,
			score = score,
			currentScore = currentScore,
			awardCount = awardCount,
			isMnc = mnc ?: false,

			sysImage = sysImage,
			imageUrl = imageUrl,
			imageKey = imageKey,
			images = images,
		)
	}
}

fun User.toUserCol(isNew: Boolean = false): UserCol {
	return UserCol(
		id = if (isNew) ObjectId().toString() else id,
		email = email,
		login = login,
		hashPassword = hashPassword,
		name = name,
		patronymic = patronymic,
		lastname = lastname,
		role = role,
		bio = bio,
		post = post,
		phone = phone,
		gender = gender,
		description = description,
		mnc = isMnc,
		companyId = companyId,
		departmentId = departmentId,
		score = score,
		currentScore = currentScore,
		awardCount = awardCount,

		sysImage = sysImage,
		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,
	)
}