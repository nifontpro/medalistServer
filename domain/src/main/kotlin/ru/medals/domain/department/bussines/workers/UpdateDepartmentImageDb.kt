package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkResponseData
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.updateDepartmentImageDb(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkResponseData {
			departmentRepository.updateImage(
				departmentId = departmentIdValid,
				imageKey = imageKeyValid,
				fileData = fileData
			)
		}
	}
}