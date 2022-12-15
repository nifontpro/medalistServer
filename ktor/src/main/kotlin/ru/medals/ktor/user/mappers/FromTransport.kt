package ru.medals.ktor.user.mappers

import ru.medals.domain.user.bussines.context.UserCommand
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.model.Gender
import ru.medals.domain.user.model.User
import ru.medals.ktor.user.model.request.*

fun UserContext.fromTransport(request: CreateUserRequest) {
	command = UserCommand.CREATE
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
	command = UserCommand.GET_BY_ID
	userId = request.userId
}

fun UserContext.fromTransport(request: GetUserByIdDepNameRequest) {
	command = UserCommand.GET_BY_ID_DEP_NAME
	userId = request.userId
}

fun UserContext.fromTransport(request: GetUserByIdAwardsRequest) {
	command = UserCommand.GET_BY_ID_AWARDS
	userId = request.userId
}

fun UserContext.fromTransport(request: GetUsersByDepartmentRequest) {
	command = UserCommand.GET_BY_DEPARTMENT
	departmentId = request.departmentId
	searchFilter = request.filter
}

fun UserContext.fromTransport(request: GetUsersByCompanyRequest) {
	command = UserCommand.GET_BY_COMPANY
	companyId = request.companyId
	searchFilter = request.filter
}

fun UserContext.fromTransport(request: GetUsersByCompanyDepNameRequest) {
	command = UserCommand.GET_BY_COMPANY_DEP_NAME
	companyId = request.companyId
	searchFilter = request.filter
}

fun UserContext.fromTransport(request: GetUsersWithAwardsRequest) {
	command = UserCommand.GET_WITH_AWARDS
	companyId = request.companyId
	searchFilter = request.filter
}

fun UserContext.fromTransport(request: GetUsersWithAwardsFullRequest) {
	command = UserCommand.GET_WITH_AWARDS_FULL
	companyId = request.companyId
	searchFilter = request.filter
}

fun UserContext.fromTransport(request: GetBestUsersByCompanyRequest) {
	command = UserCommand.GET_BEST
	companyId = request.companyId
	request.limit?.let { limit = it }
}

fun UserContext.fromTransport(request: GetBossesRequest) {
	command = UserCommand.GET_BOSSES
	companyId = request.companyId
	searchFilter = request.filter
}


fun UserContext.fromTransport(request: DeleteUserRequest) {
	command = UserCommand.DELETE
	userId = request.userId
}

fun UserContext.fromTransport(request: UpdateUserRequest) {
	command = UserCommand.UPDATE
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
	command = UserCommand.COUNT_BY_COMPANY
	companyId = request.companyId
}

fun UserContext.fromTransport(request: UserCountByDepartmentRequest) {
	command = UserCommand.COUNT_BY_DEPARTMENT
	departmentId = request.departmentId
}

fun UserContext.fromTransport(request: UserAwardCountByDepartmentRequest) {
	command = UserCommand.AWARD_COUNT_BY_DEPARTMENT
	departmentId = request.departmentId
}

fun UserContext.fromTransport(request: UserAwardCountByCompanyRequest) {
	command = UserCommand.AWARD_COUNT_BY_COMPANY
	companyId = request.companyId
}

fun UserContext.fromTransport(request: DeleteUserImageRequest) {
	command = UserCommand.IMAGE_DELETE
	userId = request.userId
	imageKey = request.imageKey
}