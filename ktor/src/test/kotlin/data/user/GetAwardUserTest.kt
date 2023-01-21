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
import ru.medals.domain.core.util.separator
import ru.medals.domain.user.repository.UserRepository
import ru.medals.s3.di.s3Module

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetAwardUserTest : KoinTest {

	private val userRepository by inject<UserRepository>(UserRepository::class.java)

	@Test
	fun `Получение награжденных сотрудников за период`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, s3Module)
			}

			val companyId = "638621902741bb167c6c2386"

			val usersAwards = userRepository.getAwardUsersByCompany(
				companyId = companyId,
				startDate = 1669738287969 + 1,
				endDate = System.currentTimeMillis()
			)

			separator()
			println(usersAwards.data)
			println(usersAwards.error)

			stopKoin()
		}
	}
}