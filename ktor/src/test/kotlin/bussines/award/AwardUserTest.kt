package bussines.award

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.award.bussines.context.AwardCommand
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.bussines.processor.AwardProcessor
import ru.medals.domain.award.model.AwardState
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.di.domainModule
import ru.medals.s3.di.s3Module

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class AwardUserTest : KoinTest {

	private val awardProcessor by lazy { AwardProcessor() }

	@Test
	fun `Проверка присвоения награды сотруднику`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, domainModule, s3Module)
			}
			val userIdd = "6386238b2741bb167c6c2388"
			val awardIdd = "6384fb75fb0baf41c3b2a10b"
			val ctx = AwardContext().apply {
				command = AwardCommand.AWARD_USER
				state = ContextState.NONE
				userId = userIdd
				awardId = awardIdd
				awardState = AwardState.NOMINEE
			}

			awardProcessor.exec(ctx)
			println("-------------------------")
			println(ctx.errors)
			println("Principal user: ${ctx.principalUser}")

//			assertEquals(1, ctx.user.id)
			stopKoin()
		}
	}
}