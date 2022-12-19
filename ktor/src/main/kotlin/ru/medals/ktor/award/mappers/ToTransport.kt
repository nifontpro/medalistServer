package ru.medals.ktor.award.mappers

import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardCount
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardUsers

fun AwardContext.toTransportGetAwards(): List<Award> = awards

fun AwardContext.toTransportGetAwardsUsers(): List<AwardUsers> = awardsUsers

fun AwardContext.toTransportGetAward(): Award = award

fun AwardContext.toTransportGetAwardUsers(): AwardUsers = awardUsers

fun AwardContext.toTransportGetAwardRelate(): AwardRelate? = awardRelate

fun AwardContext.toTransportGetAwardCount(): AwardCount = awardCount

