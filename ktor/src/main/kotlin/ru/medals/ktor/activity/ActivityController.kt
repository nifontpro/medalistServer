package ru.medals.ktor.activity

import io.ktor.server.application.*
import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.activity.bussines.processor.ActivityProcessor
import ru.medals.domain.activity.model.ActivityExt
import ru.medals.ktor.activity.mappers.fromTransport
import ru.medals.ktor.activity.mappers.toTransportGetActivities
import ru.medals.ktor.activity.model.request.GetActivitiesRequest
import ru.medals.ktor.core.process

suspend fun ApplicationCall.getActivitiesByCompany(processor: ActivityProcessor) =
	process<GetActivitiesRequest, List<ActivityExt>, ActivityContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetActivities() }
	)