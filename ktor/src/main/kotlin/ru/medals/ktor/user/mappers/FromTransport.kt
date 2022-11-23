package ru.medals.ktor.user.mappers

import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.Gender
import ru.medals.domain.user.model.User
import ru.medals.ktor.user.model.request.*

fun UserContext.fromTransport(request: CreateUserRequest) {
	command = UserContext.Command.CREATE
	user = User(
		role = request.role,
		login = request.login,
		hashPassword = request.password,
		email = request.email,
		name = request.name,
		patronymic = request.patronymic,
		lastname = request.lastname,
		bio = request.bio,
		post = request.post,
		phone = request.phone,
		gender = request.gender ?: Gender.UNDEFINED,
		description = request.description,
		companyId = request.companyId,
		departmentId = request.departmentId,
		isMnc = request.isMNC,
	)
}

fun UserContext.fromTransport(request: GetUserByIdRequest) {
	command = UserContext.Command.GET_BY_ID
	userId = request.userId
}

fun UserContext.fromTransport(request: GetUsersByDepartmentRequest) {
	command = UserContext.Command.GET_BY_DEPARTMENT
	departmentId = request.departmentId
	searchFilter = request.filter
}

fun UserContext.fromTransport(request: GetBestUsersByCompanyRequest) {
	command = UserContext.Command.GET_BEST
	companyId = request.companyId
	request.limit?.let { limit = it }
}

fun UserContext.fromTransport(request: GetBossesRequest) {
	command = UserContext.Command.GET_BOSSES
	companyId = request.companyId
	searchFilter = request.filter
}


fun UserContext.fromTransport(request: DeleteUserRequest) {
	command = UserContext.Command.DELETE
	userId = request.userId
}

fun UserContext.fromTransport(request: UpdateUserRequest) {
	command = UserContext.Command.UPDATE
	user = User(
		id = request.id,
		login = request.login,
		hashPassword = request.password,
		email = request.email,
		name = request.name,
		patronymic = request.patronymic,
		lastname = request.lastname,
		bio = request.bio,
		post = request.post,
		phone = request.phone,
		gender = request.gender ?: Gender.UNDEFINED,
		description = request.description,
		isMnc = request.isMNC,
	)
}

fun UserContext.fromTransport(request: UserCountByCompanyRequest) {
	command = UserContext.Command.COUNT_BY_COMPANY
	companyId = request.companyId
}

fun UserContext.fromTransport(request: UserCountByDepartmentRequest) {
	command = UserContext.Command.COUNT_BY_DEPARTMENT
	departmentId = request.departmentId
}

fun UserContext.fromTransport(request: DeleteUserImageRequest) {
	command = UserContext.Command.IMAGE_DELETE
	userId = request.userId
	imageKey = request.imageKey
}