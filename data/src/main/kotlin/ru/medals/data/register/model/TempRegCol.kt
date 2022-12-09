package ru.medals.data.register.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.register.model.TempReg

data class TempRegCol(
	val userId: String? = null,
	val name: String? = null,
	val login: String? = null,
	val email: String? = null,
	val hashPassword: String? = null,
	val code: String? = null,
	val expDate: Long? = null,

	@BsonId
	val id: String = ObjectId().toString(),
) {
	fun toTempReg() = TempReg(
		userId = userId ?: "",
		name = name ?: "",
		login = login ?: "",
		email = email ?: "",
		hashPassword = hashPassword ?: " ",
		code = code ?: "",
		expDate = expDate ?: -1
	)
}

fun TempReg.toTempRegCol() = TempRegCol(
	userId = userId,
	name = name,
	login = login,
	email = email,
	hashPassword = hashPassword,
	code = code,
	expDate = expDate
)

