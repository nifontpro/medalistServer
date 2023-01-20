package ru.medals.ktor.core

import ru.medals.domain.core.bussines.BaseContext

fun BaseContext.toTransportGetCount(): Long = countResponse

fun BaseContext.toTransportGetIds(): List<String> = ids