package ru.medals.ktor.activity.mappers

import ru.medals.domain.activity.bussines.context.ActivityCommand
import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.activity.model.ActivityQuery
import ru.medals.ktor.activity.model.request.GetActivitiesRequest

fun ActivityContext.fromTransport(request: GetActivitiesRequest) {
	command = ActivityCommand.GET_BY_COMPANY
	companyId = request.companyId
	searchFilter = request.filter

	activityQuery = ActivityQuery(
		startDate = request.startDate,
		endDate = request.endDate,
		sort = request.sort,
		page = request.page,
		pageSize = request.pageSize
	)
}