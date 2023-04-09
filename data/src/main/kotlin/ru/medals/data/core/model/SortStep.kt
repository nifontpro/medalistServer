package ru.medals.data.core.model

import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.conversions.Bson
import org.litote.kmongo.sort
import ru.medals.domain.core.bussines.model.BaseQueryValid

/**
 * Шаг сортировки в запросе на получении списка объектов
 */
fun sortStep(
	baseQuery: BaseQueryValid,
	alwaysSortField: String = "name" // Обязательное поле сортировки, если есть первичное - то оно станет вторичным
): Bson {

	val direct = BsonInt32(baseQuery.direction)
	return when (baseQuery.field) {

		alwaysSortField, null -> sort(
			BsonDocument().append(alwaysSortField, direct)
		)

//			null -> skip(0)
		else -> {
			sort(
				BsonDocument()
					.append(baseQuery.field, direct)
					.append(alwaysSortField, BsonInt32(1))
			)
		}
	}
}