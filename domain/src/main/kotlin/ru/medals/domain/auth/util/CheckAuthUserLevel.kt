package ru.medals.domain.auth.util

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.User.Companion.ADMIN
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.medals.domain.user.repository.UserRepository

suspend fun checkAuthMinUser(
	userId: String,
	principalUser: User
): Boolean {
	val userRepository: UserRepository by inject(UserRepository::class.java)

	if (principalUser.role == null) {
		return false
	}


	val findUser = userRepository.getUserById(userId) ?: run {
		return false
	}

	if (principalUser.getRolePriority() < User.getRolePriorityStatic(findUser.role)) {
		return false
	}

	if (principalUser.role == findUser.role) {
		if (principalUser.id != findUser.id) {
			return false
		}
	} else when (principalUser.role) {
		OWNER -> {
			if (findUser.companyId == null || !checkAuthMinOwner(companyId = findUser.companyId, principalUser)) {
				return false
			}
		}

		ADMIN -> {
			if (principalUser.companyId != findUser.companyId) {
				return false
			}
		}

		DIRECTOR -> {
			if (principalUser.companyId != findUser.companyId || principalUser.departmentId != findUser.departmentId) {
				return false
			}
		}
	}
	return true
}