package ru.medals.domain.core.util

fun getFullUserName(
	lastname: String?,
	name: String?,
	patronymic: String?
): String {
	val ln = lastname ?: ""
	val n = name ?: ""
	val p = patronymic ?: ""
	return "$ln $n $p".trim()
}