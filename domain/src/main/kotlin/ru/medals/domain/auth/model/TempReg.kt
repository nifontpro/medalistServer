package ru.medals.domain.auth.model

data class TempReg(
    val email: String,
    val code: String,
    val expDate: Long,
)
