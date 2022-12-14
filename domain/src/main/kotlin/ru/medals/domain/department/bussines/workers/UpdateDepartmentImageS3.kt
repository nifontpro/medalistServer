package ru.medals.domain.department.bussines.workers

import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.helper.checkRepositoryBool
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.otus.cor.ICorChainDsl
import ru.otus.cor.worker

fun ICorChainDsl<DepartmentContext>.updateDepartmentImageS3(title: String) = worker {

	this.title = title
	on { state == ContextState.RUNNING }

	handle {
		checkRepositoryBool(repository = "department", "Сбой при обновлении изображения отдела") {
			departmentRepository.updateImage(
				departmentId = departmentIdValid,
				fileData = fileData
			)
		}
	}
}