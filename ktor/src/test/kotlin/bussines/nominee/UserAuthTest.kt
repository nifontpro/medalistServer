package bussines.nominee

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.di.domainModule
import ru.medals.domain.user.bussines.context.UserCommand
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.bussines.processor.UserProcessor
import ru.medals.domain.user.repository.UserRepository
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class UserAuthTest : KoinTest {

	private val userRepository by inject<UserRepository>(UserRepository::class.java)
	private val processor by lazy { UserProcessor() }

	@Test
	fun `Проверка UserAuth`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}
			val id = "6291438c923bbf7c24d1bf66"
			val user = userRepository.getUserById(id)
			println("------> $user")
			assertEquals(id, user?.id)
			stopKoin()
		}
	}

	@Test
	fun `Проверка routes`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, domainModule)
			}
			val id = "6291438c923bbf7c24d1bf66"
			val ctx = UserContext().apply {
				state = ContextState.NONE
				userId = id
				command = UserCommand.GET_BY_ID
			}

			processor.exec(ctx)
			println("--------->" + ctx.user)

			assertEquals(id, ctx.user.id)
			stopKoin()
		}
	}
}