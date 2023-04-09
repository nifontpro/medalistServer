package ru.medals.ktor.user.model.request

data class SaveUserSettingRequest(
	val userId: String = "",
	val showOnboarding: Boolean = false,
	val pageOnboarding: Int = -1,
)