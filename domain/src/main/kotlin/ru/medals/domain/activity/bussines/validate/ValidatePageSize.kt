package ru.medals.domain.activity.bussines.validate

import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<ActivityContext>.validatePageSize(title: String) = worker {
	this.title = title
	on { activityQuery.pageSize?.let { it < 1 } ?: false }
	handle {
		fail(
			errorValidation(
				field = "pageSize",
				violationCode = "not valid",
				description = "Размер страниц не может быть меньше 1"
			)
		)
	}
}