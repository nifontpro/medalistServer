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
import ru.medals.domain.appoint.repository.AppointRepository
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetAwardsMedalByUsersTest : KoinTest {

	private val appointRepository by inject<AppointRepository>(AppointRepository::class.java)

	@Test
	fun `Получение награждений сотрудника с медалями`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val userId = "63639ce5d08088291dfe538b"

			val appointAwards = appointRepository.getAppointAwardsWithMedalByUsers(userId = userId).data
			println("------------------")
			println(appointAwards)

			assertEquals("Бронзовая", appointAwards?.first()?.awardMedal?.medal?.name)

			stopKoin()
		}
	}
}