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
import ru.medals.domain.di.domainModule
import ru.medals.domain.user.repository.UserRepository
import ru.medals.s3.di.s3Module

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GetUserAwardCountTest : KoinTest {

	private val userRepository by inject<UserRepository>(UserRepository::class.java)

	@Test
	fun `Получение количества сотрудников с наградами`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, domainModule, s3Module)
			}

			val companyId = "63641544e81cbd0b5c5a8412"

			userRepository.getAwardCountByCompany(companyId = companyId)

			stopKoin()
		}
	}
}