package ru.medals.domain.activity.bussines.validate

import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<ActivityContext>.validatePage(title: String) = worker {
	this.title = title
	on { activityQuery.page?.let { it < 0 } ?: false }
	handle {
		fail(
			errorValidation(
				field = "page",
				violationCode = "not valid",
				description = "Страница не может быть меньше 0"
			)
		)
	}
}