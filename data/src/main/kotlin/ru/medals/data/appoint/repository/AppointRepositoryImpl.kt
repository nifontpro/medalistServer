package ru.medals.data.appoint.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.medals.data.appoint.model.AppointCol
import ru.medals.data.appoint.model.toAppointCol
import ru.medals.data.appoint.repository.AppointErrors.Companion.errorUserNotFound
import ru.medals.data.award.model.AwardCol
import ru.medals.data.award.repository.AwardErrors
import ru.medals.data.award.repository.AwardErrors.Companion.errorAwardNotFound
import ru.medals.data.user.model.UserCol
import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.repository.AppointRepository
import ru.medals.domain.core.bussines.model.RepositoryData

class AppointRepositoryImpl(
	db: CoroutineDatabase
) : AppointRepository {

	private val appoints = db.getCollection<AppointCol>()
	private val users = db.getCollection<UserCol>()
	private val awards = db.getCollection<AwardCol>()

	override suspend fun create(appoint: Appoint): RepositoryData<Appoint> {

		if (awards.findOneById(appoint.awardId) == null) return errorAwardNotFound()
		if (users.findOneById(appoint.userId) == null) return errorUserNotFound()

		val appointCol = appoint.toAppointCol(create = true)
		return if (appoints.insertOne(appointCol).wasAcknowledged()) {
			RepositoryData.success(data = appointCol.toAppoint())
		} else {
			AwardErrors.errorCreate()
		}
	}

}