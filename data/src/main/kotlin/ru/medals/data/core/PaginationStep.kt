package ru.medals.data.core

import org.bson.conversions.Bson
import org.litote.kmongo.limit
import org.litote.kmongo.skip
import ru.medals.domain.core.bussines.model.BaseQuery

fun skipStep(baseQuery: BaseQuery): Bson {
	val skipSize = (baseQuery.page ?: 0) * (baseQuery.pageSize ?: 0)
	return skip(skipSize)
}

fun limitStep(baseQuery: BaseQuery): Bson {
	val pageSize = baseQuery.pageSize ?: Int.MAX_VALUE
	return limit(pageSize)
}