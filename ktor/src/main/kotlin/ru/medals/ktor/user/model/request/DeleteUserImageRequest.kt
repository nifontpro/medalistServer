package ru.medals.ktor.user.model.request

data class DeleteUserImageRequest(
	val userId: String? = null,
	val imageKey: String? = null,
)