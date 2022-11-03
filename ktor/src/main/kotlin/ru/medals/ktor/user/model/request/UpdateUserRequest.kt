package ru.medals.ktor.user.model.request

data class UpdateUserRequest(
	val id: String = "",
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val bio: String? = null,
	val login: String? = null,
	val password: String? = null,
	val email: String? = null,
	val isMNC: Boolean? = null
)