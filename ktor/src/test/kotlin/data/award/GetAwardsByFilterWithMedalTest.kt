package data.award

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.award.repository.AwardRepository
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetAwardsByFilterWithMedalTest : KoinTest {

	private val awardRepository by inject<AwardRepository>(AwardRepository::class.java)

	@Test
	fun `Получение наград с медалью`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val companyId = "63641544e81cbd0b5c5a8412"

			val awards = awardRepository.getAwardsByFilterMedal(companyId = companyId).data
			println(awards)

			assertEquals("Бронзовая", awards?.first()?.medal?.name)
			stopKoin()
		}
	}
}