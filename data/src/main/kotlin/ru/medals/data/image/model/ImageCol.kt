package ru.medals.data.image.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.Binary
import org.bson.types.ObjectId
import java.util.*

data class ImageCol(
	val image: Binary,
	val fileName: String? = null,

	val createDate: Date? = null,
	val updateDate: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
)

data class TimeCol(

	val timestamp: Date? = null,

	@BsonId
	val id: String = ObjectId().toString()
)

