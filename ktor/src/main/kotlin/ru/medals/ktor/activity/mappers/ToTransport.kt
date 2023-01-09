package ru.medals.ktor.activity.mappers

import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.activity.model.ActivityExt

fun ActivityContext.toTransportGetActivities(): List<ActivityExt> = activities