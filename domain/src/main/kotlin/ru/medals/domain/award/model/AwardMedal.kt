package ru.medals.domain.award.model

import ru.medals.domain.medal.model.Medal

data class AwardMedal(
	val name: String? = null,
	val description: String? = null,
	val createDate: Long? = null,
	val companyId: String? = null,
	val medal: Medal? = null,
	val criteria: String? = null,
	val status: AwardStatus? = null,

	val id: String = ""
)