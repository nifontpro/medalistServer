package ru.medals.domain.register.model

data class TempReg(
	val userId: String = "",
	val name: String = "",
	val login: String = "",
	val email: String = "",
	val hashPassword: String = "",
	val code: String = "",
	val expDate: Long = 0,
)
