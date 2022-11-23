package ru.medals.ktor.user.model.request

import ru.medals.domain.user.model.Gender

data class UpdateUserRequest(
	val id: String = "",
	val email: String? = null,
	val login: String? = null,
	val password: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val bio: String? = null,
	val post: String? = null,
	val phone: String? = null,
	val gender: Gender? = null,
	val description: String? = null,

	val isMNC: Boolean? = null
)