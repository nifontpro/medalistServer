package ru.medals.domain.setting.model

data class UserSetting(
	val userId: String = "",
	val showOnboarding: Boolean = false,
	val pageOnboarding: Int = -1,
	val found: Boolean = false,
)