package ru.medals.domain.award.model

data class AwardCount(
	val before: Long = 0,
	val nominee: Long = 0,
	val after: Long = 0,
	val total: Long = 0,
	val error: Long = 0,
)
