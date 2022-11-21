package ru.medals.ktor.register.model.request

data class CreateValidOwnerRequest(
	val email: String? = null,
	val code: String? = null, // code by email
)
