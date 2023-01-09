package ru.medals.domain.activity.model

@Suppress("unused")
enum class ActivityState {
	NONE,
	NOMINEE_USER,
	AWARD_USER,
	DELETE_AWARD_USER,  // Удаление награждения у сотрудника
	DELETE_AWARD,
	DELETE_USER
}