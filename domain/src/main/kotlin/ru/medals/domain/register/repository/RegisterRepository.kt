package ru.medals.domain.register.repository

import ru.medals.domain.register.model.TempReg

interface RegisterRepository {
    suspend fun createTempReg(tempReg: TempReg): Boolean
    suspend fun getTempRegByEmail(email: String): TempReg?
    suspend fun checkTempRegExist(email: String): Boolean
}