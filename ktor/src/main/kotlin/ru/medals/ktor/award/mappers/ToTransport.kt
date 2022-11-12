package ru.medals.ktor.award.mappers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardMedal

fun AwardContext.toTransportGetAwards(): List<AwardMedal> = awards

fun AwardContext.toTransportGetAward(): Award = award

fun AwardContext.toTransportGetAwardMedal(): AwardMedal = awardMedal

