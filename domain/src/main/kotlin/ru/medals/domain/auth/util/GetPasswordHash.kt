package ru.medals.domain.auth.util

fun main() {
	val password = "test123"
	val hash = hashPassword(password)
	println(hash)
}