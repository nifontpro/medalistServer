package ru.medals.data.auth.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.gt
import org.litote.kmongo.lt
import ru.medals.data.auth.model.TempRegCol
import ru.medals.data.auth.model.toTempRegCol
import ru.medals.domain.auth.model.TempReg
import ru.medals.domain.auth.repository.AuthRepository

class AuthRepositoryImpl(
	db: CoroutineDatabase
) : AuthRepository {
	private val tempRegsCol = db.getCollection<TempRegCol>()

	private suspend fun clearOldTempReg() {
		tempRegsCol.deleteMany(filter = TempRegCol::expDate lt System.currentTimeMillis()) // <
	}

	override suspend fun checkTempRegExist(email: String): Boolean {
		clearOldTempReg()
		return tempRegsCol.findOne(filter = TempRegCol::email eq email) != null
	}

	override suspend fun createTempReg(tempReg: TempReg) {
		tempRegsCol.insertOne(tempReg.toTempRegCol())
	}

	override suspend fun getRegCodeByEmail(email: String): String? {
		val tempRegCol = tempRegsCol.findOne(
			TempRegCol::email eq email,
			TempRegCol::expDate gt System.currentTimeMillis() // >
		)
		return tempRegCol?.code
	}
}