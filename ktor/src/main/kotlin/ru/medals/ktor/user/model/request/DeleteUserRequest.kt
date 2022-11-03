package ru.medals.ktor.user.model.request

data class DeleteUserRequest(
	val userId: String? = null,
)