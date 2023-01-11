package ru.medals.domain.core.bussines

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.helper.ContextError
import ru.medals.domain.core.bussines.model.BaseQuery
import ru.medals.domain.core.bussines.model.BaseQueryValid
import ru.medals.domain.core.util.Constants.TEST_MODE
import ru.medals.domain.image.model.FileData
import ru.medals.domain.message.repository.MessageRepository
import ru.medals.domain.user.model.User
import ru.medals.domain.user.repository.UserRepository

interface IBaseCommand

abstract class BaseContext(

	var companyId: String? = null,
	var departmentId: String? = null,
	var userId: String? = null,
	var medalId: String = "",

	var companyIdValid: String = "",
	var departmentIdValid: String = "",
	var userIdValid: String = "",

	var baseQuery: BaseQuery = BaseQuery(),
	var baseQueryValid: BaseQueryValid = BaseQueryValid(),
	var searchFilter: String? = null, // deprecated

	var principalUser: User = User(),
	var userFIO: String = "",
	var timeStart: Long = -1,

	var command: IBaseCommand,
	var state: ContextState = ContextState.NONE,

	var limit: Int = Int.MAX_VALUE,

	// Для работы с изображениями:
	var imageEntityId: String = "",
	var imageKey: String? = null,
	var imageKeyValid: String = "",
	var fileData: FileData = FileData(),

	// Для отпраки сообщений
//	var message: Message = Message(),

	// Возвращаемые параметры:
	var responseId: String = "",
	var countResponse: Long = -1,
	val errors: MutableList<ContextError> = mutableListOf(),

	var testMode: Boolean = false,
) {
	fun prodMode() = !(testMode && TEST_MODE)

	val messageRepository: MessageRepository by inject(MessageRepository::class.java)
	val userRepository: UserRepository by inject(UserRepository::class.java)
}

@Suppress("unused")
enum class ContextState {
	NONE,
	STARTING, // Старт процесора
	RUNNING,  // Старт операции
	FAILING,
	FINISHING,
}