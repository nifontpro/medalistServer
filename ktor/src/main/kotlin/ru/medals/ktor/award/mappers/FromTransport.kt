package ru.medals.ktor.award.mappers

import ru.medals.domain.award.bussines.context.AwardCommand
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.Award
import ru.medals.domain.core.bussines.model.BaseQuery
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.ktor.award.model.request.*

fun AwardContext.fromTransport(request: CreateAwardRequest) {
	command = AwardCommand.CREATE
	award = Award(
		name = request.name ?: "",
		description = request.description,
		criteria = request.criteria,
		startDate = request.startDate,
		endDate = request.endDate,
		score = request.score,
		companyId = request.companyId ?: "",
	)
	companyId = request.companyId // Для полной авторизации
}

fun AwardContext.fromTransport(request: UpdateAwardRequest) {
	command = AwardCommand.UPDATE
	awardId = request.id ?: ""
	award = Award(
		id = awardId,
		name = request.name ?: "",
		description = request.description,
		criteria = request.criteria,
		startDate = request.startDate,
		endDate = request.endDate,
		score = request.score,
	)
}

fun AwardContext.fromTransport(request: DeleteAwardRequest) {
	command = AwardCommand.DELETE
	awardId = request.awardId ?: ""
}

fun AwardContext.fromTransport(request: GetAwardsByCompanyRequest) {
	command = AwardCommand.GET_BY_COMPANY
	searchFilter = request.filter
	companyId = request.companyId
}

fun AwardContext.fromTransport(request: GetAllAwardsByCompanyRequest) {
	command = AwardCommand.GET_BY_COMPANY_WITH_USERS
	searchFilter = request.filter
	companyId = request.companyId

	baseQuery = BaseQuery(
		page = request.page,
		pageSize = request.pageSize,
		filter = request.filter,
		field = request.field,
		direction = request.direction
	)
}

fun AwardContext.fromTransport(request: GetAwardByIdRequest) {
	command = AwardCommand.GET_BY_ID
	awardId = request.awardId ?: ""
}

fun AwardContext.fromTransport(request: GetAwardByIdUsersRequest) {
	command = AwardCommand.GET_BY_ID_WITH_USERS
	awardId = request.awardId ?: ""
}

fun AwardContext.fromTransport(request: AwardUserRequest) {
	command = AwardCommand.AWARD_USER
	awardId = request.awardId ?: ""
	userId = request.userId
	awardState = request.awardState
}

fun AwardContext.fromTransport(request: AwardUserDeleteRequest) {
	command = AwardCommand.AWARD_USER_DELETE
	awardId = request.awardId ?: ""
	userId = request.userId
}

fun AwardContext.fromTransport(request: GetAwardCountByCompanyRequest) {
	command = AwardCommand.GET_COUNT
	companyId = request.companyId
}

fun AwardContext.fromTransport(request: AwardDeleteMainImageRequest) {
	command = AwardCommand.DELETE_IMAGE_OLD
	awardId = request.awardId ?: ""
}

fun AwardContext.fromTransport(request: SetAwardGalleryImageRequest) {
	command = AwardCommand.SET_GALLERY_IMAGE
	awardId = request.awardId
	galleryItem = GalleryItem(id = request.galleryItemId)
}

fun AwardContext.fromTransport(@Suppress("UNUSED_PARAMETER") request: GetIdsRequest) {
	command = AwardCommand.GET_IDS
}
