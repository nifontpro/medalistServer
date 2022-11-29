package ru.medals.data.award.repository

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import ru.medals.data.award.model.AwardCol
import ru.medals.data.award.model.AwardUsersCol
import ru.medals.data.award.model.toAwardColCreate
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorAwardCreate
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorAwardDelete
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorAwardNotFound
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorAwardUpdate
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorAwardUser
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorGetAward
import ru.medals.domain.award.model.*
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.image.model.FileData
import ru.medals.domain.image.repository.S3Repository
import java.util.*

class AwardRepositoryImpl(
	db: CoroutineDatabase,
	private val s3repository: S3Repository
) : AwardRepository {

	private val awards = db.getCollection<AwardCol>()

	override suspend fun create(award: Award): RepositoryData<Award> {

		val awardCol = award.toAwardColCreate()
		return if (awards.insertOne(awardCol).wasAcknowledged()) {
			RepositoryData.success(data = awardCol.toAward())
		} else {
			errorAwardCreate()
		}
	}

	override suspend fun delete(id: String): RepositoryData<Award> {
		val awardCol = awards.findOneById(id) ?: return errorAwardNotFound()
		return if (awards.deleteOneById(id).wasAcknowledged()) {
			RepositoryData.success(data = awardCol.toAward())
		} else {
			errorAwardDelete()
		}
	}

	override suspend fun update(award: Award): RepositoryData<Unit> {
		return try {
			awards.updateOneById(
				id = award.id,
				update = set(
					AwardCol::name setTo award.name,
					AwardCol::description setTo award.description,
					AwardCol::criteria setTo award.criteria,
					AwardCol::startDate setTo award.startDate?.let { Date(it) },
					AwardCol::endDate setTo award.endDate?.let { Date(it) },
					AwardCol::criteria setTo award.criteria,
				)
			)
			RepositoryData.success()
		} catch (e: Exception) {
			errorAwardUpdate()
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
			errorGetAward()
		}
	}

	/**
	 * Получить награду с прикреплеными сотрудниками
	 * и от кто назначил награду (fromUser)
	 */
	override suspend fun getByIdWithUsers(awardId: String): RepositoryData<AwardUsers> {
		return try {
			// Исправить ошибку - from -> list
			val awardUsers = awards.aggregate<AwardUsersCol>(
				match(AwardCol::id eq awardId),
				lookup(from = "userCol", localField = "relations.userId", foreignField = "_id", newAs = "users"),
				lookup(
					from = "userCol",
					localField = "relations.nomineeUserId",
					foreignField = "_id",
					newAs = "fromNomineeUsers"
				),
				lookup(from = "userCol", localField = "relations.awardUserId", foreignField = "_id", newAs = "fromAwardUsers"),
			).toList().firstOrNull()?.toAwardUser()
			RepositoryData.success(data = awardUsers)
		} catch (e: Exception) {
			errorGetAward()
		}
	}

	override suspend fun getByCompany(companyId: String, filter: String?): RepositoryData<List<Award>> {
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
			errorGetAward()
		}
	}

	override suspend fun getAwardsWithUsers(
		companyId: String,
		filter: String?
	): RepositoryData<List<AwardUsers>> {
		return try {
			val awardsUsers = awards.aggregate<AwardUsersCol>(
				match(
					and(
						AwardCol::companyId eq companyId,
						filter?.let {
							AwardCol::name regex Regex("$filter", RegexOption.IGNORE_CASE)
						}
					)
				),
				lookup(from = "userCol", localField = "relations.userId", foreignField = "_id", newAs = "users"),
			).toList().map { it.toAwardUser() }
			RepositoryData.success(data = awardsUsers)
		} catch (e: Exception) {
			errorGetAward()
		}
	}

	/**
	 * Приставляем сотрудника к награде
	 */
	override suspend fun awardUser(awardId: String, awardRelate: AwardRelate, isNew: Boolean): RepositoryData<Unit> {
//		awards.updateOneById(
//			id =  awardId,
//			pullByFilter(AwardCol::relations, AwardRelate::userId eq userId)
//		)

		return try {
			if (isNew) {
				awards.updateOneById(
					id = awardId,
					update = addToSet(AwardCol::relations, awardRelate)
				)
			} else {
				awards.updateOne(
					filter = and(AwardCol::id eq awardId, AwardCol::relations / AwardRelate::userId eq awardRelate.userId),
					update = setValue(AwardCol::relations.posOp, awardRelate)
				)
			}
			RepositoryData.success()
		} catch (e: Exception) {
			errorAwardUser()
		}
	}

	/**
	 * Получить запись о награждении сотрудника определенной наградой
	 * и companyId
	 */
	override suspend fun getAwardRelateFromUser(
		awardId: String,
		userId: String
	): RepositoryData<AwardRelate> {
		return try {
			val awardCol = awards.aggregate<AwardCol>(
				"[" +
						"{\$match: {'_id': '$awardId', 'relations.userId': '$userId'}}" +
						"{\$project:{'_id': 1, 'name': 1, 'companyId': 1, " +
						"relations: {\$filter: {input: '\$relations', as: 'rel', cond: {\$eq: ['\$\$rel.userId', '$userId']}}}" +
						"}}" +
						"]"
			).toList().firstOrNull()
			val relate = awardCol?.relations?.firstOrNull()
			RepositoryData.success(data = relate)
		} catch (e: Exception) {
			errorGetAward()
		}
	}

	@Deprecated("Удалить в будущем")
	override suspend fun updateImage(
		awardId: String,
		fileData: FileData,
	): Boolean {
		try {
			val awardCol = awards.findOneById(awardId) ?: return false
			val imageKey = "C${awardCol.companyId}/awards/${fileData.filename}"
			val imageUrl = s3repository.putObject(key = imageKey, fileData = fileData) ?: return false

			val isSuccess = awards.updateOneById(
				id = awardId,
				update = set(
					AwardCol::imageUrl setTo imageUrl,
					AwardCol::imageKey setTo imageKey
				),
			).wasAcknowledged()

			if (isSuccess) {
				// Удаляем старое изображение в s3
				awardCol.imageKey?.let {
					s3repository.deleteObject(key = it)
				}
			} else {
				// Удаляем новое изображение в s3, если не обновились данные в БД
				s3repository.deleteObject(key = imageKey)
			}

			return isSuccess
		} catch (e: Exception) {
			println(e.stackTrace)
			return false
		}
	}
}