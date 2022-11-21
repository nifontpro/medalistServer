package ru.medals.data.appoint.repository

import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.lookup
import org.litote.kmongo.match
import org.litote.kmongo.unwind
import ru.medals.data.appoint.model.AppointAwardCol
import ru.medals.data.appoint.model.AppointAwardMedalCol
import ru.medals.data.appoint.model.AppointCol
import ru.medals.data.appoint.model.AppointUserCol
import ru.medals.data.appoint.repository.AppointErrors.Companion.errorAppointCreate
import ru.medals.data.appoint.repository.AppointErrors.Companion.errorAppointDelete
import ru.medals.data.appoint.repository.AppointErrors.Companion.errorAppointGet
import ru.medals.data.appoint.repository.AppointErrors.Companion.errorAppointNotFound
import ru.medals.data.appoint.repository.AppointErrors.Companion.errorUserNotFound
import ru.medals.data.award.model.AwardCol
import ru.medals.data.award.repository.AwardRepoErrors.Companion.errorAwardNotFound
import ru.medals.data.user.model.UserCol
import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.model.AppointAward
import ru.medals.domain.appoint.model.AppointAwardMedal
import ru.medals.domain.appoint.model.AppointUser
import ru.medals.domain.appoint.repository.AppointRepository
import ru.medals.domain.core.bussines.model.RepositoryData
import java.util.*

class AppointRepositoryImpl(
	db: CoroutineDatabase
) : AppointRepository {

	private val appoints = db.getCollection<AppointCol>()
	private val users = db.getCollection<UserCol>()
	private val awards = db.getCollection<AwardCol>()

	override suspend fun create(appoint: Appoint): RepositoryData<Appoint> {

		if (awards.findOneById(appoint.awardId) == null) return errorAwardNotFound()
		if (users.findOneById(appoint.userId) == null) return errorUserNotFound()

		val appointCol = AppointCol(
			awardId = appoint.awardId,
			userId = appoint.userId,
			status = appoint.status,
			nomineeDate = appoint.nomineeDate?.let { Date(it) },
			rewardDate = appoint.rewardDate?.let { Date(it) },
			id = ObjectId().toString()
		)

		return try {
			appoints.insertOne(appointCol)
			RepositoryData.success(data = appointCol.toAppoint())
		} catch (e: Exception) {
			errorAppointCreate()
		}
	}

	override suspend fun delete(id: String): RepositoryData<Appoint> {
		val appointCol = appoints.findOneById(id) ?: return errorAppointNotFound()
		return if (awards.deleteOneById(id).wasAcknowledged()) {
			RepositoryData.success(data = appointCol.toAppoint())
		} else {
			errorAppointDelete()
		}
	}

	override suspend fun getAppointAwardsByUsers(userId: String): RepositoryData<List<AppointAward>> {
		return try {
			val appointAwards = appoints.aggregate<AppointAwardCol>(
				match(AppointCol::userId eq userId),
				lookup(from = "awardCol", localField = "awardId", foreignField = "_id", newAs = "award"),
				unwind("\$award")
			).toList().map { it.toAppointAward() }
			RepositoryData.success(data = appointAwards)
		} catch (e: Exception) {
			errorAppointGet()
		}
	}

	override suspend fun getAppointAwardsWithMedalByUsers(userId: String): RepositoryData<List<AppointAwardMedal>> {
		return try {
			val appointAwards = appoints.aggregate<AppointAwardMedalCol>(
				match(AppointCol::userId eq userId),
				lookup(from = "awardCol", localField = "awardId", foreignField = "_id", newAs = "awardMedal"),
				unwind("\$awardMedal"),
				lookup(from = "medalCol", localField = "awardMedal.medalId", foreignField = "_id", newAs = "awardMedal.medal"),
				unwind("\$awardMedal.medal")
			).toList().map { it.toAppointAwardMedal() }
			RepositoryData.success(data = appointAwards)
		} catch (e: Exception) {
			errorAppointGet()
		}
	}

	override suspend fun getAppointUsersByAward(awardId: String): RepositoryData<List<AppointUser>> {
		return try {
			val appointUsers = appoints.aggregate<AppointUserCol>(
				match(AppointCol::awardId eq awardId),
				lookup(from = "userCol", localField = "userId", foreignField = "_id", newAs = "user"),
				unwind("\$user"),

			).toList().map { it.toAppointUser() }
			RepositoryData.success(data = appointUsers)
		} catch (e: Exception) {
			errorAppointGet()
		}
	}

	/*
	group(RewardCol::id, RewardCol::signatures.push(RewardCol::signatures from RewardCol::dateActive))

	db.orders.aggregate([
    // Unwind the source
    { "$unwind": "$products" },
    // Do the lookup matching
    { "$lookup": {
       "from": "products",
       "localField": "products",
       "foreignField": "_id",
       "as": "productObjects"
    }},
    // Unwind the result arrays ( likely one or none )
    { "$unwind": "$productObjects" },
    // Group back to arrays
    { "$group": {
        "_id": "$_id",
        "products": { "$push": "$products" },
        "productObjects": { "$push": "$productObjects" }
    }}
])
	 */

}