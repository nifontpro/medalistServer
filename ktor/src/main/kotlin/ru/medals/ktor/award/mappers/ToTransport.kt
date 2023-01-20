package ru.medals.ktor.award.mappers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.*

fun AwardContext.toTransportGetAwards(): List<Award> = awards

fun AwardContext.toTransportGetAwardsUsers(): List<AwardUsers> = awardsUsers

fun AwardContext.toTransportGetAward(): Award = award

fun AwardContext.toTransportGetAwardLite(): AwardLite = awardLite

fun AwardContext.toTransportGetAwardUsers(): AwardUsers = awardUsers

fun AwardContext.toTransportGetAwardRelate(): AwardRelate? = awardRelate

fun AwardContext.toTransportGetAwardCount(): AwardCount = awardCount