package ru.medals.domain.appoint.repository

import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.core.bussines.model.RepositoryData

interface AppointRepository {
	suspend fun create(appoint: Appoint): RepositoryData<Appoint>
}