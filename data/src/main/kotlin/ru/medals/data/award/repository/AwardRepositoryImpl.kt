package ru.medals.data.award.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.award.model.AwardCol
import ru.medals.data.award.model.toAwardCol
import ru.medals.domain.award.model.Award
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

class AwardRepositoryImpl(
	db: CoroutineDatabase
) : AwardRepository {
	private val awards = db.getCollection<AwardCol>()

	override suspend fun create(award: Award): String? {
		val awardCol = award.toAwardCol()
		return if (awards.insertOne(awardCol).wasAcknowledged()) {
			awardCol.id
		} else {
			null
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

	companion object {

		const val REPO = "award"

		private fun errorAwardNotFound() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "not found",
				description = "Награждение не найдено"
			)
		)

		private fun errorDelete() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "delete error",
				description = "Ошибка удаления награды"
			)
		)

	}
}