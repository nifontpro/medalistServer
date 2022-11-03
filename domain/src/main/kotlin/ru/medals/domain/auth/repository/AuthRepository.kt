package ru.medals.domain.auth.repository

import ru.medals.domain.auth.model.TempReg

interface AuthRepository {
    suspend fun createTempReg(tempReg: TempReg)
    suspend fun getRegCodeByEmail(email: String): String?
    suspend fun checkTempRegExist(email: String): Boolean
}