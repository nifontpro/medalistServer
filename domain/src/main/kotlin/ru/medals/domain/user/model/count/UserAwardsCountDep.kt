package ru.medals.domain.user.model.count

data class UserAwardsCountDep(
	val id: DepName = DepName(),
	val userAwardCount: Long = 0,
	val userNomineeCount: Long = 0
)
