package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryData
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.medals.domain.user.model.User
import ru.medals.domain.user.model.User.Companion.OWNER
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.createOwner(title: String) = worker {

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

		user = User(
			name = tempReg.name,
			login = tempReg.login,
			email = tempReg.email,
			hashPassword = tempReg.hashPassword,
			role = OWNER,
			score = 0,
			currentScore = 0,
			awardCount = 0,
		)

		user = checkRepositoryData {
			userRepository.createUser(user = user)
		} ?: return@handle

	}
}