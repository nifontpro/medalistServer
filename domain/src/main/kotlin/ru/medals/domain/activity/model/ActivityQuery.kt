package ru.medals.domain.activity.model

data class ActivityQuery(
	val companyId: String = "",
	val startDate: Long = 0,
	val endDate: Long = Long.MAX_VALUE,
	val filter: String? = null,
	val sort: Int = -1, // Направление сортировки по дате
	val page: Int? = null,
	val pageSize: Int? = null
)