package ru.medals.domain.register.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.core.util.Constants
import ru.medals.domain.register.bussines.context.RegisterContext
import ru.medals.domain.register.model.TempReg
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<RegisterContext>.saveTempRegUserToDb(title: String) = worker {
	this.title = title
	on { state == ContextState.RUNNING }
	handle {
		checkRepositoryBool(
			repository = "register",
			description = "Ошибка временной регистрации"
		) {
			registerRepository.createTempReg(
				TempReg(
					name = user.name ?: "",
					login = user.login ?: "",
					email = user.email ?: "",
					password = user.hashPassword ?: "",
					code = code,
					expDate = System.currentTimeMillis() + Constants.LIFE_TIME_REGISTER_TOKEN
				)
			)
		}
	}
}
