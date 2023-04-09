package ru.medals.ktor.user.model.request

data class GetUserSettingRequest(
	val userId: String = "",
)