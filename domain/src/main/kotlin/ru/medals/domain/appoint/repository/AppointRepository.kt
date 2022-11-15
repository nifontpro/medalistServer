package ru.medals.domain.appoint.repository

import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.model.AppointAward
import ru.medals.domain.appoint.model.AppointAwardMedal
import ru.medals.domain.appoint.model.AppointUser
import ru.medals.domain.core.bussines.model.RepositoryData

interface AppointRepository {
	suspend fun create(appoint: Appoint): RepositoryData<Appoint>
	suspend fun delete(id: String): RepositoryData<Appoint>
	suspend fun getAppointAwardsByUsers(userId: String): RepositoryData<List<AppointAward>>
	suspend fun getAppointAwardsWithMedalByUsers(userId: String): RepositoryData<List<AppointAwardMedal>>
	suspend fun getAppointUsersByAward(awardId: String): RepositoryData<List<AppointUser>>
}