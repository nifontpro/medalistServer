package ru.medals.ktor.core.request

interface BaseRequest {
	val page: Int?
	val pageSize: Int?
	val filter: String? // Поле для сортровки
	val field: String? // Направление сортировки
	val direction: Int?
}