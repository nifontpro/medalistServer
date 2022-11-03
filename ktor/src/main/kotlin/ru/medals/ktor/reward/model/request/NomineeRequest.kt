package ru.medals.ktor.reward.model.request

import ru.medals.domain.reward.model.Nominee

data class NomineeRequest(
	var name: String = "",
	var description: String? = null,
	var score: Int = 0,
	var userId: String = "",
	var medalId: String = "",
	var companyId: String = ""
) {
	fun toNominee() = Nominee(
		name = name,
		description = description,
		score = score,
		userId = userId,
		medalId = medalId,
		companyId = companyId
	)
}