package ru.medals.domain.department.bussines.context

import org.koin.java.KoinJavaComponent.inject
import ru.medals.domain.core.bussines.BaseContext
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.department.model.Department
import ru.medals.domain.department.repository.DepartmentRepository
import ru.medals.domain.user.repository.UserRepository

data class DepartmentContext(

	var department: Department = Department(),
	var departmentUpdate: Department = Department(),
	var departments: List<Department> = emptyList(),

	) : BaseContext(command = Command.NONE) {

	val departmentRepository: DepartmentRepository by inject(DepartmentRepository::class.java)
	val userRepository: UserRepository by inject(UserRepository::class.java)

	enum class Command : IBaseCommand {
		NONE,
		CREATE_EMPTY,
		CREATE,
		DELETE,
		GET_BY_ID,
		GET_BY_COMPANY,
		COUNT_BY_COMPANY,
		UPDATE,
		UPDATE_IMAGE,
		IMAGE_ADD,
		IMAGE_UPDATE,
		IMAGE_DELETE
	}
}