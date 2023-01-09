package data.activity

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.activity.model.ActivityQuery
import ru.medals.domain.activity.repository.ActivityRepository

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetActivityTest : KoinTest {

	private val activityRepository by inject<ActivityRepository>(ActivityRepository::class.java)

	@Test
	fun `Получаем активность в компании`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val activityQuery = ActivityQuery(
				companyId = "638621902741bb167c6c2386",
				startDate = 0,
				endDate = Long.MAX_VALUE,
				filter = null,
				page = 1,
				pageSize = 1
			)

			activityRepository.getByCompany(activityQuery)

			stopKoin()
		}
	}
}