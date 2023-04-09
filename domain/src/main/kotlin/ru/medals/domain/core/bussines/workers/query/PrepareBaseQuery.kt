package ru.medals.domain.core.bussines.workers.query

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.model.BaseQueryValid
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.prepareBaseQuery(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {

		/**
		 * Проверка поля field должна быть на уровне локального контекста
		 */

		with(baseQuery) {
			if (page != null && pageSize != null && page >= 0 && pageSize > 0) {
				baseQueryValid = BaseQueryValid(page = page, pageSize = pageSize)
			}

			baseQueryValid = baseQueryValid.copy(
				filter = if (filter.isNullOrBlank()) null else filter.trim(),
				field = if (field.isNullOrBlank()) null else field.trim(),
				direction = when {
					direction == null -> 1
					direction < 0 -> -1
					else -> 1
				}
			)
		}
	}
}
