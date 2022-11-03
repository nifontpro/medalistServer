package ru.medals.ktor.auth.model.request

data class LoginRequest(
    val email: String,
    val password: String
)