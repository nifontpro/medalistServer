package ru.medals.domain.company.model

data class Company(
	val name: String = "",
	val description: String? = null,

	val ownerId: String = "", // id владельца компании
	val imageUrl: String? = null,

	val id: String = ""
)
