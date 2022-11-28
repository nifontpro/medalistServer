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
import ru.medals.domain.award.model.AwardRelate
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.s3.di.s3Module

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class AwardUserTest : KoinTest {

	private val awardRepository by inject<AwardRepository>(AwardRepository::class.java)

	@Test
	fun `Присвоение награды сотруднику`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, s3Module)
			}

			val awardId = "6384fb75fb0baf41c3b2a10b"
//			val awardId = "6384fba9fb0baf41c3b2a10c" // 2
			val userId = "63639ce5d08088291dfe538b" // Владелец
//			val userId = "6380b3d2cd265368bbb23634" // Киселева
			val fromUserId = "63639ce5d08088291dfe538b"
			val state = AwardState.NOMINEE

			val awardRelate = AwardRelate(
				userId = userId,
				state = state,
				nomineeUserId = fromUserId,
				awardUserId = "User FF"
			)

			awardRepository.awardUser(
				awardId = awardId,
				awardRelate = awardRelate,
				isNew = false
			)

			stopKoin()
		}
	}
}