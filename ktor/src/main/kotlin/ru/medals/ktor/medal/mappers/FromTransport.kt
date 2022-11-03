package ru.medals.ktor.medal.mappers

import ru.medals.domain.medal.bussines.context.MedalContext
import ru.medals.domain.medal.model.Medal
import ru.medals.ktor.medal.model.request.*

fun MedalContext.fromTransport(request: CreateMedalRequest) {
	command = MedalContext.Command.CREATE
	companyId = request.companyId
	updateMedal = Medal(companyId = request.companyId, isSystem = request.isSystem)
}

fun MedalContext.fromTransport(request: DeleteMedalRequest) {
	command = MedalContext.Command.DELETE
	medalId = request.medalId
}


fun MedalContext.fromTransport(request: UpdateMedalRequest) {
	command = MedalContext.Command.UPDATE
	medalId = request.id
	updateMedal = Medal(
		id = request.id,
		name = request.name,
		description = request.description,
		score = request.score
	)
}

fun MedalContext.fromTransport(request: GetMedalByIdRequest) {
	command = MedalContext.Command.GET_BY_ID
	medalId = request.medalId ?: ""
}

fun MedalContext.fromTransport(request: GetMedalsByCompanyRequest) {
	command = MedalContext.Command.GET_BY_COMPANY
	companyId = request.companyId
	searchFilter = request.filter
}

fun MedalContext.fromTransport(request: GetCountMedalsByCompanyRequest) {
	command = MedalContext.Command.GET_COUNT
	companyId = request.companyId
}

fun MedalContext.fromTransport(request: DeleteMedalImageRequest) {
	command = MedalContext.Command.IMAGE_DELETE
	medalId = request.medalId ?: ""
	imageKey = request.imageKey
}