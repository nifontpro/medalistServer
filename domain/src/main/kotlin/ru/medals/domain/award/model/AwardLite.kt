package ru.medals.domain.award.model

data class AwardLite(
    val name: String = "",
    val imageUrl: String? = null,
    val state: AwardState = AwardState.NONE,

    val id: String = ""
)