package ru.medals.domain.activity.bussines.validate

import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<ActivityContext>.validateSort(title: String) = worker {
	this.title = title
	on { !listOf(1, -1).contains(activityQuery.sort) }
	handle {
		fail(
			errorValidation(
				field = "sort",
				violationCode = "not valid",
				description = "Неверный признак сортировки"
			)
		)
	}
}