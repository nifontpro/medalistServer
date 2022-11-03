package ru.medals.domain.reward.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.errorDb
import ru.medals.domain.core.bussines.helper.errorValidation
import ru.medals.domain.core.bussines.helper.fail
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RewardContext>.getUserByIdFromDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }
	except {
		fail(
			errorDb(
				repository = "user",
				violationCode = "internal",
				description = "Сбой получения сотрудника"
			)
		)
	}
	handle {
		val user = userRepository.getUserById(nominee.userId) ?: run {
			fail(
				errorDb(
					repository = "user",
					violationCode = "not found",
					description = "Сотрудник не найден"
				)
			)
			return@handle
		}

		if (user.companyId != nominee.companyId) {
			fail(
				errorValidation(
					field = "companyId",
					violationCode = "not equal",
					description = "Неверная компания сотрудника"
				)
			)
			return@handle
		}

		// Подготовка к авторизации
		companyId = user.companyId
		departmentId = user.departmentId
	}
}