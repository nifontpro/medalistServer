package ru.medals.data.auth.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.auth.model.TempReg

data class TempRegCol(
	val email: String,
	val code: String,
	val expDate: Long,

	@BsonId
	val id: String = ObjectId().toString(),
)

fun TempReg.toTempRegCol() = TempRegCol(
	email = email,
	code = code,
	expDate = expDate,
)

