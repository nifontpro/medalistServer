package ru.medals.data.user.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.medal.model.MedalCol
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.medal.model.Medal
import ru.medals.domain.user.model.MedalInfo
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserMedals

data class UserMedalsCol(
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

	val medalsInfo: List<MedalInfoCol> = emptyList(),
	val medals: List<MedalCol>,

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {
	fun toUserMedal() = UserMedals(
		id = id,
		email = email,
		login = login,
		name = name,
		patronymic = patronymic,
		lastname = lastname,
		hashPassword = null,
		role = role ?: User.NONE,
		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,
		bio = bio,
		companyId = companyId,
		departmentId = departmentId,
		score = score,
		currentScore = currentScore,
		rewardCount = rewardCount,
		medalsInfo = medalsInfo.map { medalInfoCol ->
			MedalInfo(
				medal = medals.find { medal -> medalInfoCol.medalId == medal.id }?.toMedal() ?: Medal(),
				createDate = medalInfoCol.createDate.time,
				score = medalInfoCol.score,
				fromUserId = medalInfoCol.fromUserId
			)
		}
	)
}