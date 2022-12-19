package ru.medals.domain.user.model

data class UserAwardCount(
	val total: Long = 0,
	val awards: Long = 0,
	val nominee: Long = 0,
)