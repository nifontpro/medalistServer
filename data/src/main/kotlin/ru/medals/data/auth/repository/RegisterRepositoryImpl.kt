package ru.medals.data.auth.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.gt
import org.litote.kmongo.lt
import org.litote.kmongo.regex
import ru.medals.data.auth.model.TempRegCol
import ru.medals.data.auth.model.toTempRegCol
import ru.medals.domain.register.model.TempReg
import ru.medals.domain.register.repository.RegisterRepository

class RegisterRepositoryImpl(
	db: CoroutineDatabase
) : RegisterRepository {
	private val tempRegsCol = db.getCollection<TempRegCol>()

	/**
	 * Удаляем устаревшие по времени записи
	 */
	private suspend fun clearOldTempReg() {
		tempRegsCol.deleteMany(filter = TempRegCol::expDate lt System.currentTimeMillis())
	}

	override suspend fun checkTempRegExist(email: String): Boolean {
		clearOldTempReg()
		return tempRegsCol.countDocuments(
			TempRegCol::email regex Regex("^$email\$", RegexOption.IGNORE_CASE)
		) > 0
	}

	override suspend fun createTempReg(tempReg: TempReg): Boolean {
		return try {
			tempRegsCol.insertOne(tempReg.toTempRegCol())
			true
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun getRegCodeByEmail(email: String): String? {
		val tempRegCol = tempRegsCol.findOne(
			TempRegCol::email eq email,
			TempRegCol::expDate gt System.currentTimeMillis() // >
		)
		return tempRegCol?.code
	}

}