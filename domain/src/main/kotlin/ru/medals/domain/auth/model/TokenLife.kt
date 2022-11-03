package ru.medals.domain.auth.model

data class TokenLife(
    val token: String,
    val expirationDate: Long // Срок годности токена (Unix ms)
)
