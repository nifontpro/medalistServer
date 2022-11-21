package ru.medals.data.register.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.register.model.TempReg

data class TempRegCol(
	val name: String,
	val login: String,
	val email: String,
	val hashPassword: String,
	val code: String,
	val expDate: Long,

	@BsonId
	val id: String = ObjectId().toString(),
) {
	fun toTempReg() = TempReg(
		name = name,
		login = login,
		email = email,
		hashPassword = hashPassword,
		code = code,
		expDate = expDate
	)
}

fun TempReg.toTempRegCol() = TempRegCol(
	name = name,
	login = login,
	email = email,
	hashPassword = hashPassword,
	code = code,
	expDate = expDate
)

