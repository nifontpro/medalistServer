package ru.medals.ktor.award.mappers

import mu.KotlinLogging
import org.slf4j.LoggerFactory
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.model.Award
import ru.medals.ktor.award.model.request.*

//val log = Logger.getLogger("ROUTING")
private val log = KotlinLogging.logger {}

fun AwardContext.fromTransport(request: CreateAwardRequest) {
	command = AwardContext.Command.CREATE
	award = Award(
		name = request.name,
		description = request.description,
		companyId = request.companyId,
		medalId = request.medalId,
		criteria = request.criteria,
	)
	companyId = request.companyId // Для полной авторизации
}

fun AwardContext.fromTransport(request: DeleteAwardRequest) {
	command = AwardContext.Command.DELETE
	awardId = request.awardId ?: ""
}

fun AwardContext.fromTransport(request: GetAwardsByCompanyRequest) {
	command = AwardContext.Command.GET_BY_COMPANY
	searchFilter = request.filter
	companyId = request.companyId
	log.info("Mylog: company id: $companyId")
}

fun AwardContext.fromTransport(request: GetAwardByIdRequest) {
	command = AwardContext.Command.GET_BY_ID
	awardId = request.awardId ?: ""
}

fun AwardContext.fromTransport(request: UpdateAwardRequest) {
	val logger = LoggerFactory.getLogger(this::class.java)
	command = AwardContext.Command.UPDATE
	awardId = request.id ?: ""
	award = Award(
		id = awardId,
		name = request.name,
		description = request.description,
		medalId = request.medalId,
		criteria = request.criteria,
	)
	logger.error(award.toString())
}