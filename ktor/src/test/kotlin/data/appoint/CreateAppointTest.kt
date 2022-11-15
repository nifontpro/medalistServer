package data.appoint

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.appoint.model.Appoint
import ru.medals.domain.appoint.model.AppointStatus
import ru.medals.domain.appoint.repository.AppointRepository

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class CreateAppointTest : KoinTest {

	private val appointRepository by inject<AppointRepository>(AppointRepository::class.java)

	@Test
	fun `Создание связи номинация-сотрудник`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val awardId = "636ea66d5a4b81241e635ab0"
			val userId = "63639ce5d08088291dfe538b"

			appointRepository.create(Appoint(
				awardId = awardId,
				userId = userId,
				status = AppointStatus.NOMINEE
			))

			stopKoin()
		}
	}
}