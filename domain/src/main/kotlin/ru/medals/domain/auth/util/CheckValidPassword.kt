package ru.medals.domain.auth.util

import org.mindrot.jbcrypt.BCrypt

fun checkValidPassword(enteredPassword: String, hashPassword: String): Boolean {
    return try {
        BCrypt.checkpw(enteredPassword, hashPassword)
    } catch (e: Exception) {
        false
    }
}