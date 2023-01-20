package ru.medals.data.core.model

import org.bson.codecs.pojo.annotations.BsonId

data class IdCol(
	@BsonId
	val id: String = ""
)