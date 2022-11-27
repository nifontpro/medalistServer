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
import ru.medals.s3.di.s3Module

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetAwardsWithUserTest : KoinTest {

	private val awardRepository by inject<AwardRepository>(AwardRepository::class.java)

	@Test
	fun `Получение наград с сотрудником`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, s3Module)
			}

			awardRepository.getAwardsWithUsers()
			stopKoin()
		}
	}
}