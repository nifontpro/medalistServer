package ru.medals.domain.core.response

import ru.medals.domain.core.bussines.BaseContext

fun <T : BaseContext> T.baseResponseId(): IdResponse = IdResponse(
	id = responseId
)