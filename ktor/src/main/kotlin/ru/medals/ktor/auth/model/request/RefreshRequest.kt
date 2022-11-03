package ru.medals.ktor.auth.model.request

data class RefreshRequest(
	val filter: String? = null
)