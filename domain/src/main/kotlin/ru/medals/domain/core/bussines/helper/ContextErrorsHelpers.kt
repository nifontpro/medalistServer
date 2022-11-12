package ru.medals.domain.core.bussines.helper

import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.model.RepositoryData

/*fun Throwable.asContextError(
	code: String = "unknown",
	group: String = "exceptions",
	message: String = this.message ?: "",
) = ContextError(
	code = code,
	group = group,
	field = "",
	message = message,
	exception = this,
)*/

fun BaseContext.addError(error: ContextError) = errors.add(error)

fun BaseContext.fail(error: ContextError) {
	addError(error)
	state = ContextState.FAILING
}

suspend fun <T> BaseContext.checkResponseData(operation: suspend () -> RepositoryData<T>): T? {
	val result = operation()
	return if (result.success) {
		result.data
	} else {
		addError(
			errorDb(
				repository = result.error?.repository ?: "undefined repository",
				violationCode = result.error?.violationCode ?: "error",
				description = result.error?.description ?: "Внутренняя ошибка БД"
			)
		)
		state = ContextState.FAILING
		null
	}
}

suspend fun BaseContext.checkRepositoryResponseId(
	repository: String,
	description: String,
	operation: suspend () -> String?
) {
	val result = operation()
	if (result != null) {
		responseId = result
	} else {
		addError(
			errorDb(
				repository = repository,
				violationCode = "internal error",
				description = description
			)
		)
		state = ContextState.FAILING
	}
}

suspend fun BaseContext.checkRepositoryBool(
	repository: String,
	description: String,
	operation: suspend () -> Boolean
) {
	val result = operation()
	if (!result) {
		addError(
			errorDb(
				repository = repository,
				violationCode = "internal",
				description = description
			)
		)
		state = ContextState.FAILING
	}
}

fun errorValidation(
	field: String,
	/**
	 * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
	 * Например: empty, badSymbols, tooLong, etc
	 */
	violationCode: String,
	description: String,
	level: ContextError.Levels = ContextError.Levels.INFO,
) = ContextError(
	code = "validation-$field-$violationCode",
	field = field,
	group = "validation",
	message = description,
	level = level,
)

fun errorDb(
	repository: String,
	violationCode: String,
	description: String,
	level: ContextError.Levels = ContextError.Levels.ERROR,
) = ContextError(
	code = "db-$repository-$violationCode",
	field = repository,
	group = "db",
	message = "Ошибка БД: $description",
	level = level,
)

fun errorUnauthorized(
	role: String, // Уровень доступа
	description: String,
	level: ContextError.Levels = ContextError.Levels.UNAUTHORIZED,
) = ContextError(
	code = "unauthorized-$role",
	field = role,
	group = "unauthorized",
	message = "Недостаточно прав для совершения операции (минимум $description)",
	level = level,
)

fun otherError(
	description: String,
	field: String,
	code: String = "other",
	level: ContextError.Levels = ContextError.Levels.INFO,
) = ContextError(
	code = code,
	field = field,
	group = "other",
	message = description,
	level = level,
)