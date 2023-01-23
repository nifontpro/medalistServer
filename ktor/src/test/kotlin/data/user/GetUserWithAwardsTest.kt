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
class GetUserWithAwardsTest : KoinTest {

	private val userRepository by inject<UserRepository>(UserRepository::class.java)

	@Test
	fun `Получение сотрудников с наградами`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, s3Module)
			}

			val companyId = "638621902741bb167c6c2386"

			val usersAwards = userRepository.getUsersByCompanyWithAwards(
				companyId = companyId,

			).data
			println(usersAwards)

			stopKoin()
		}
	}
}