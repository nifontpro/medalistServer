package data.user

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.user.repository.UserRepository
import ru.medals.s3.di.s3Module

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetUserWithAwardsUnionTest : KoinTest {

	private val userRepository by inject<UserRepository>(UserRepository::class.java)

	@Test
	fun `Получение сотрудника с наградами`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, s3Module)
			}

			val userId = "6386238b2741bb167c6c2388"

			userRepository.getUserByIdWithAwards(userId)
//			println(usersAwards)

			stopKoin()
		}
	}
}