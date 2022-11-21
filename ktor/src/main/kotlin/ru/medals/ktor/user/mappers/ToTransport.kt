package ru.medals.ktor.user.mappers

import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User

fun UserContext.toTransportGetUser(): User = user.copy(hashPassword = null)

fun UserContext.toTransportGetUsers(): List<User> = users

