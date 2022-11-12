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
class GetAwardByIdWithMedalTest : KoinTest {

	private val awardRepository by inject<AwardRepository>(AwardRepository::class.java)

	@Test
	fun `Получение награды с медалью`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val awardId = "636ea7b45a4b81241e635ab1"

			val award = awardRepository.getAwardByIdWithMedal(awardId).data
			println(award)

			assertEquals(awardId, award?.id)
			assertEquals("636416a7e81cbd0b5c5a8416", award?.medal?.id)

			stopKoin()
		}
	}
}