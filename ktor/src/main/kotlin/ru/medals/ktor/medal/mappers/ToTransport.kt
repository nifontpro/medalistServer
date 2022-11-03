package ru.medals.ktor.medal.mappers

import ru.medals.domain.medal.bussines.context.MedalContext
import ru.medals.domain.medal.model.Medal

fun MedalContext.toTransportGetById(): Medal = medal

fun MedalContext.toTransportGetByCompany(): List<Medal> = medals