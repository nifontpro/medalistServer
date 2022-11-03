package ru.medals.domain.reward.model

data class Nominee(
	var name: String = "",
	var description: String? = null,
	var score: Int = 0,
	var userId: String = "",
	var medalId: String = "",
	var companyId: String = ""
)