package ru.medals.domain.core.bussines.validate

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun <T : BaseContext> ICorChainDsl<T>.prepareFilter(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		searchFilter = if (searchFilter.isNullOrBlank()) {
			null
		} else {
			searchFilter?.trim()
		}
	}
}
