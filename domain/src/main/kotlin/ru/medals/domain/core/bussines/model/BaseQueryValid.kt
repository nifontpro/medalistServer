package ru.medals.domain.core.bussines.model

data class BaseQueryValid(
	val page: Int = 0,
	val pageSize: Int = Int.MAX_VALUE,
	val filter: String? = null,
	val field: String? = null, // Поле для сортировки
	val direction: Int = 1, // Направление сортировки
)