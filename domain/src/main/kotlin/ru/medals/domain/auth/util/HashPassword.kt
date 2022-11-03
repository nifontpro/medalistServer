package ru.medals.domain.auth.util

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}

fun main() {
    val password = "password"
    println(hashPassword(password))
}