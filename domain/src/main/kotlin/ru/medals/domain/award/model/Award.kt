package ru.medals.domain.award.model

data class Award(
	val name: String? = null,
	val description: String? = null,
	val createDate: Long? = null,
	val companyId: String? = null,
	val medalId: String? = null,
	val criteria: String? = null,
	val status: AwardStatus? = null,

	val id: String = ""
)

@Suppress("unused")
enum class AwardStatus {
	NONE, NOMINEE, AWARD_ALL
}
