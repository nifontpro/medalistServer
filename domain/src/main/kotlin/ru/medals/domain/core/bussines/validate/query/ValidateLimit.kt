package ru.medals.domain.core.bussines.validate.query

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.validateBaseQuery(title: String) = worker {
	this.title = title
	on { baseQuery.page?.let { it < 0 } ?: false || baseQuery.pageSize?.let { it < 0 } ?: false }
	handle {
		fail(
			errorValidation(
				field = "pages",
				violationCode = "not positive",
				description = "Страницы не могут быть отрицательными числами"
			)
		)
	}
}
