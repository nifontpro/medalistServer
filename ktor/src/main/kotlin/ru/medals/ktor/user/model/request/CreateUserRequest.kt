package ru.medals.ktor.user.model.request

import ru.medals.domain.user.model.Gender

data class CreateUserRequest(
	val role: String? = null,
	val login: String? = null,
	val email: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val bio: String? = null,
	val post: String? = null,
	val phone: String? = null,
	val gender: Gender? = null,
	val description: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val isMNC: Boolean? = null,

	val test: Boolean = false
)