package ru.medals.domain.core.util

import ru.medals.domain.core.util.Constants.PASSWORD_LENGTH

private val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun getRandomPassword() = List(PASSWORD_LENGTH) { chars.random() }.joinToString("")