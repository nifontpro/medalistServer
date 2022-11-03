package ru.medals.domain.auth.bussines.workers

import ru.medals.domain.auth.bussines.context.AuthContext
import ru.medals.domain.auth.util.hashPassword
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryResponseId
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<AuthContext>.createOwner(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Сбой создания владельца компаний"
			)
		)
	}
	handle {

		user = user.copy(
			email = email,
			hashPassword = hashPassword(password),
			role = OWNER,
			score = 0,
			currentScore = 0,
			rewardCount = 0,
		)

		checkRepositoryResponseId(repository = "user", "Сбой создания владельца компаний") {
			userRepository.createUser(user = user)
		}

		user = user.copy(id = responseId)
	}
}