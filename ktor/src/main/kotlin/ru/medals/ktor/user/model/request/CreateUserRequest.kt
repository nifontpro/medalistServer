package ru.medals.ktor.user.model.request

data class CreateUserRequest(
	val role: String = "",
	val login: String = "",
	val password: String = "",
	val email: String? = null,
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val bio: String? = null,
	val companyId: String? = null,
	val departmentId: String? = null,
	val isMNC: Boolean = false
)