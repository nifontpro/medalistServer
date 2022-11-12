package ru.medals.data.award.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.award.model.AwardCol
import ru.medals.data.award.model.AwardMedalCol
import ru.medals.data.award.model.toAwardCol
import ru.medals.data.award.repository.AwardErrors.Companion.errorAwardNotFound
import ru.medals.data.award.repository.AwardErrors.Companion.errorCreate
import ru.medals.data.award.repository.AwardErrors.Companion.errorDelete
import ru.medals.data.award.repository.AwardErrors.Companion.errorIO
import ru.medals.data.award.repository.AwardErrors.Companion.errorMedalNotFound
import ru.medals.data.award.repository.AwardErrors.Companion.errorUpdate
import ru.medals.data.medal.model.MedalCol
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.model.AwardMedal
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.model.RepositoryData

class AwardRepositoryImpl(
	db: CoroutineDatabase
) : AwardRepository {

	private val awards = db.getCollection<AwardCol>()
	private val medals = db.getCollection<MedalCol>()

	override suspend fun create(award: Award): RepositoryData<Award> {
		award.medalId?.let {
			if (medals.findOneById(it) == null) return errorMedalNotFound()
		}

		val awardCol = award.toAwardCol(create = true)
		return if (awards.insertOne(awardCol).wasAcknowledged()) {
			RepositoryData.success(data = awardCol.toAward())
		} else {
			errorCreate()
		}
	}

	override suspend fun delete(id: String): RepositoryData<Award> {
		val awardCol = awards.findOneById(id) ?: return errorAwardNotFound()
		return if (awards.deleteOneById(id).wasAcknowledged()) {
			RepositoryData.success(data = awardCol.toAward())
		} else {
			errorDelete()
		}
	}

	override suspend fun update(award: Award): RepositoryData<Unit> {
		val awardCol = award.toAwardCol()
		println(awardCol)
		return if (awards.updateOneById(
				id = award.id,
				update = awardCol
			).wasAcknowledged()
		) {
			RepositoryData.success()
		} else {
			errorUpdate()
		}
	}

	override suspend fun getById(id: String): RepositoryData<Award> {
		return try {
			val awardCol = awards.findOneById(id = id)?.toAward()
			if (awardCol != null) {
				RepositoryData.success(data = awardCol)
			} else {
				errorAwardNotFound()
			}
		} catch (e: Exception) {
			errorIO()
		}
	}

	override suspend fun getAwardByIdWithMedal(id: String): RepositoryData<AwardMedal> {
		return try {
			val awardMedalCol = awards.aggregate<AwardMedalCol>(
				match(AwardCol::id eq id),
				lookup(from = "medalCol", localField = "medalId", foreignField = "_id", newAs = "medals"),
			).toList().firstOrNull()

			if (awardMedalCol != null) {
				RepositoryData.success(data = awardMedalCol.toAwardMedal())
			} else {
				errorAwardNotFound()
			}
		} catch (e: Exception) {
			errorIO()
		}
	}

	override suspend fun getByFilter(companyId: String, filter: String?): RepositoryData<List<Award>> {
		return try {
			val awardList = awards.find(
				AwardCol::companyId eq companyId,
				filter?.let {
					AwardCol::name regex Regex("$filter", RegexOption.IGNORE_CASE)
				}
			)
				.ascendingSort(AwardCol::name)
				.toList().map { it.toAward() }
			RepositoryData.success(data = awardList)
		} catch (e: Exception) {
			errorIO()
		}
	}

	override suspend fun getAwardsByFilterMedal(companyId: String, filter: String?): RepositoryData<List<AwardMedal>> {
		return try {

				val filterBson = filter?.let {
				and(
					AwardCol::companyId eq companyId,
					AwardCol::name regex Regex("$filter", RegexOption.IGNORE_CASE)
				)
			} ?: kotlin.run {
				AwardCol::companyId eq companyId
			}

			val awardList = awards.aggregate<AwardMedalCol>(
				match(filterBson),
				lookup(from = "medalCol", localField = "medalId", foreignField = "_id", newAs = "medals")
			).toList().map { it.toAwardMedal() }

			RepositoryData.success(data = awardList)
		} catch (e: Exception) {
			errorIO()
		}
	}
}