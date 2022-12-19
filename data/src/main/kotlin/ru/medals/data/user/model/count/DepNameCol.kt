package ru.medals.data.user.model.count

import org.bson.codecs.pojo.annotations.BsonId
import ru.medals.domain.user.model.count.DepName

data class DepNameCol(
	@BsonId
	val id: String? = null,
	val name: String? = null
) {
	fun toDepName() = DepName(
		id = id,
		name = name
	)
}