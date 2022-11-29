package ru.medals.data.award.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.data.user.model.UserCol
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardRelateUser
import ru.medals.domain.award.model.AwardUsers
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.user.model.User
import java.util.*

data class AwardUsersCol(
	val name: String,
	val description: String? = null,
	val criteria: String? = null,
	val startDate: Date? = null,
	val endDate: Date? = null,
	val score: Int? = null,
	val companyId: String,
	val relations: List<AwardRelate> = emptyList(),
	val users: List<UserCol> = emptyList(),
	val fromNomineeUsers: List<UserCol> = emptyList(),
	val fromAwardUsers: List<UserCol> = emptyList(),

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {
	fun toAwardUser() = AwardUsers(
		name = name,
		description = description,
		criteria = criteria,
		startDate = startDate?.time,
		endDate = endDate?.time,
		companyId = companyId,
		score = score,

		relateUsers = relations.map { awardRelate ->
			AwardRelateUser(
				user = users.find { user -> awardRelate.userId == user.id }?.toUser() ?: User(),
				state = awardRelate.state,
				nomineeDate = awardRelate.nomineeDate,
				awardDate = awardRelate.awardDate,
				fromNomineeUser = fromNomineeUsers.find { user -> awardRelate.nomineeUserId == user.id }?.toUser(),
				fromAwardUser = fromAwardUsers.find { user -> awardRelate.awardUserId == user.id }?.toUser(),
			)
		},

		id = id
	)
}