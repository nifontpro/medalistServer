package ru.medals.data.user.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.user.model.User

data class UserCol(
	val email: String? = null,
	val login: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val hashPassword: String? = null,
	val role: String? = null,

	val profileImageUrl: String? = null,
	val imageKey: String? = null,
	val images: List<ImageRef>? = null,

	val bio: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val score: Int? = null,
	val currentScore: Int? = null,
	val rewardCount: Int? = null,
	val mnc: Boolean? = null, // Является ли членом номинационной коммисии

	@BsonId
	val id: String = ObjectId().toString()
) {

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
			imageUrl = profileImageUrl,
			bio = bio,
			companyId = companyId,
			departmentId = departmentId,
			score = score,
			currentScore = currentScore,
			rewardCount = rewardCount,
			isMnc = mnc ?: false,
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
		profileImageUrl = imageUrl,
		bio = bio,
		mnc = isMnc,
		companyId = companyId,
		departmentId = departmentId,
		score = score,
		currentScore = currentScore,
		rewardCount = rewardCount
	)
}