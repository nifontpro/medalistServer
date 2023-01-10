package ru.medals.domain.core.bussines.model

data class BaseQuery(
	val page: Int? = null,
	val pageSize: Int? = null,
	val filter: String? = null,
	val field: String? = null,
	val direction: Int? = null,
)