package ru.medals.data.register.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.gt
import org.litote.kmongo.lt
import org.litote.kmongo.regex
import ru.medals.data.register.model.TempRegCol
import ru.medals.data.register.model.toTempRegCol
import ru.medals.domain.register.model.TempReg
import ru.medals.domain.register.repository.RegisterRepository

class RegisterRepositoryImpl(
	db: CoroutineDatabase
) : RegisterRepository {
	private val tempRegs = db.getCollection<TempRegCol>()

	/**
	 * Удаляем устаревшие по времени записи
	 */
	private suspend fun clearOldTempReg() {
		tempRegs.deleteMany(filter = TempRegCol::expDate lt System.currentTimeMillis())
	}

	override suspend fun checkTempRegExist(email: String): Boolean {
		clearOldTempReg()
		return tempRegs.countDocuments(
			TempRegCol::email regex Regex("^$email\$", RegexOption.IGNORE_CASE)
		) > 0
	}

	override suspend fun createTempReg(tempReg: TempReg): Boolean {
		return try {
			tempRegs.insertOne(tempReg.toTempRegCol())
			true
		} catch (e: Exception) {
			false
		}
	}

	override suspend fun getTempRegByEmail(email: String): TempReg? {
		return tempRegs.findOne(
			TempRegCol::email regex Regex("^$email\$", RegexOption.IGNORE_CASE),
			TempRegCol::expDate gt System.currentTimeMillis() // >
		)?.toTempReg()
	}

}