package ru.medals.domain.core.util

fun verifyEmailPassword(login: String, hash: String): Boolean {
    /*val key = email * 7 + username * 3 + email * 8

    return try {
        BCrypt.checkpw(key, hash)
    } catch (e: Exception) {
        false
    }*/

    return hash == "Register $login"
}