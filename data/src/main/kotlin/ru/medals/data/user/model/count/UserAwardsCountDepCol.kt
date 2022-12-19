package ru.medals.data.user.model.count

import org.bson.codecs.pojo.annotations.BsonId
import ru.medals.domain.user.model.count.UserAwardsCountDep

data class UserAwardsCountDepCol(
	@BsonId
	val id: DepNameCol = DepNameCol(),
	val userAwardCount: Long = 0,
	val userNomineeCount: Long = 0
) {
	fun toUserAwardsCountDep() = UserAwardsCountDep(
		id = id.toDepName(),
		userAwardCount = userAwardCount,
		userNomineeCount = userNomineeCount
	)
}
