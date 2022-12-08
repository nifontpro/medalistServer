package ru.medals.ktor.user.mappers

import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserAwardsLite

fun UserContext.toTransportGetUser(): User = user.copy(hashPassword = null)

fun UserContext.toTransportGetUsers(): List<User> = users

fun UserContext.toTransportGetUsersAwards(): List<UserAwardsLite> = usersAwards

