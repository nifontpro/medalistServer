package ru.medals.data.image.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.Binary
import org.bson.types.ObjectId

data class MessageCol(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val imageUrl: String?,
    val image: Binary,
    @BsonId
    val id: String = ObjectId().toString(),
)