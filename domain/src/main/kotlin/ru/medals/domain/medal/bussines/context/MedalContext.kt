package ru.medals.domain.medal.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.department.repository.DepartmentRepository
import ru.medals.domain.medal.model.Medal
import ru.medals.domain.medal.repository.MedalRepository

data class MedalContext(

	var medal: Medal = Medal(),
	var updateMedal: Medal = Medal(),
	var medals: List<Medal> = emptyList(),

	) : BaseContext(command = Command.NONE) {

	val medalRepository: MedalRepository by inject(MedalRepository::class.java)
	val departmentRepository: DepartmentRepository by inject(DepartmentRepository::class.java)

	enum class Command : IBaseCommand {
		NONE,
		CREATE,
		DELETE,
		GET_BY_ID,
		GET_BY_COMPANY,
		GET_COUNT,
		UPDATE,
		UPDATE_IMAGE_OLD,
		IMAGE_ADD,
		IMAGE_UPDATE,
		IMAGE_DELETE
	}
}