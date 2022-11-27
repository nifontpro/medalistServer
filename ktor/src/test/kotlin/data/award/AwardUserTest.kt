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

//			val awardId = "638257447323956c9b6ec380"
			val awardId = "63826d83c004cb0e1a0b4b29" // 2
//			val userId = "63639ce5d08088291dfe538b" // Владелец
			val userId = "6380b3d2cd265368bbb23634" // Киселева
			val fromUserId = "63639ce5d08088291dfe538b"
			val state = AwardState.NOMINEE

			val award = awardRepository.awardUser(
				awardId = awardId,
				userId = userId,
				fromUserId = fromUserId,
				state = state
			)

			stopKoin()
		}
	}
}