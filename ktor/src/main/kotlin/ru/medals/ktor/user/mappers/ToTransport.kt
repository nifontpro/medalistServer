package ru.medals.ktor.user.mappers

import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.UserAwardCount
import ru.medals.domain.user.model.UserAwardsLite
import ru.medals.domain.user.model.UserAwardsUnion

fun UserContext.toTransportGetUser(): User = user.copy(hashPassword = null)

fun UserContext.toTransportGetUserAwards(): UserAwardsUnion = userAwards

fun UserContext.toTransportGetUsers(): List<User> = users

fun UserContext.toTransportGetUsersAwardsLite(): List<UserAwardsLite> = usersAwardsLite

fun UserContext.toTransportGetUsersAwardsUnion(): List<UserAwardsUnion> = usersAwardsUnion

fun UserContext.toTransportGetUsersAwardCount(): UserAwardCount = userAwardCount

