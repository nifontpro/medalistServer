package ru.medals.domain.award.model

data class Award(
	val createDate: Long = -1,
	val medalId: String = "",
	val criteria: String? = null,
	val description: String? = null,

	val id: String
)
