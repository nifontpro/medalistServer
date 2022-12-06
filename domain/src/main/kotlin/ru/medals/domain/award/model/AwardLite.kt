package ru.medals.domain.award.model

data class AwardLite(
    val name: String = "",
    val state: AwardState = AwardState.NONE,
    val imageUrl: String? = null,

    val id: String = ""
)