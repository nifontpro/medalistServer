package ru.medals.domain.user.model

data class UserLite(
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val post: String? = null,
	val imageUrl: String? = null,

	val id: String = ""
)