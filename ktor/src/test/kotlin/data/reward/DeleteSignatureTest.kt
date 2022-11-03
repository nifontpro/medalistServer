package data.reward

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.reward.repository.RewardRepository
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class DeleteSignatureTest : KoinTest {

	private val rewardRepository by inject<RewardRepository>(RewardRepository::class.java)

	@Test
	fun `Удаление подписи ЧНК`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val rewardId = "63236fb4da8b763d838c3649"
			val mncId = "test"

			rewardRepository.putSignature(rewardId = rewardId, mncId = mncId)
			val reward = rewardRepository.getRewardById(rewardId = rewardId)
			assertEquals(true, reward?.signatures?.find { it.mncId == "test" } != null)

			rewardRepository.deleteSignature(rewardId = rewardId, mncId = mncId)

			stopKoin()
		}
	}
}