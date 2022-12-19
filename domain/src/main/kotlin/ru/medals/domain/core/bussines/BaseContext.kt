package ru.medals.domain.core.bussines

import ru.medals.domain.core.bussines.helper.ContextError
import ru.medals.domain.core.util.Constants.TEST_MODE
import ru.medals.domain.image.model.FileData
import ru.medals.domain.user.model.User

interface IBaseCommand

abstract class BaseContext(

	var companyId: String? = null,
	var departmentId: String? = null,
	var userId: String? = null,
	var medalId: String = "",

	var companyIdValid: String = "",
	var departmentIdValid: String = "",
	var userIdValid: String = "",

	var searchFilter: String? = null,

	var principalUser: User = User(),
	var timeStart: Long = -1,

	var command: IBaseCommand,
	var state: ContextState = ContextState.NONE,

	var limit: Int = Int.MAX_VALUE,

	// Для работы с изображениями:
	var imageEntityId: String = "",
	var imageKey: String? = null,
	var imageKeyValid: String = "",
	var fileData: FileData = FileData(),

	// Возвращаемые параметры:
	var responseId: String = "",
	var countResponse: Long = -1,
	val errors: MutableList<ContextError> = mutableListOf(),

	var testMode: Boolean = false,
) {
	fun prodMode() = !(testMode && TEST_MODE)
}

@Suppress("unused")
enum class ContextState {
	NONE,
	STARTING, // Старт процесора
	RUNNING,  // Старт операции
	FAILING,
	FINISHING,
}