package ru.medals.domain.register.repository

import ru.medals.domain.register.model.TempReg

interface RegisterRepository {
    suspend fun createTempReg(tempReg: TempReg): Boolean
    suspend fun getRegCodeByEmail(email: String): String?
    suspend fun checkTempRegExist(email: String): Boolean
}