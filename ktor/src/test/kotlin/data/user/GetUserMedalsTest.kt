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
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetUserMedalsTest : KoinTest {

	private val userRepository by inject<UserRepository>(UserRepository::class.java)

	@Test
	fun `Получение сотрудника с медалями`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, s3Module)
			}

			val userId = "63641774e81cbd0b5c5a8419"

			val userMedals = userRepository.getUserByIdWithMedals(userId).data

			println("---------------------------------")
			println(userMedals)

			assertEquals("Золотая", userMedals?.medalsInfo?.first()?.medal?.name)

			stopKoin()
		}
	}
}