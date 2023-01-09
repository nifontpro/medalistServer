package ru.medals.domain.activity.bussines.validate

import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<ActivityContext>.validateAwardNameEmpty(title: String) = worker {
	this.title = title
	on { activityQuery.startDate > activityQuery.endDate }
	handle {
		fail(
			errorValidation(
				field = "date",
				violationCode = "empty",
				description = "Начальная дата запроса превышает конечную"
			)
		)
	}
}