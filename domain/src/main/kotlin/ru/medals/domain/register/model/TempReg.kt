package ru.medals.domain.register.model

data class TempReg(
	val name: String = "",
	val login: String = "",
	val email: String = "",
	val password: String = "",
	val code: String = "",
	val expDate: Long = 0,
)
