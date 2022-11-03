package s3

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.domain.image.repository.S3Repository
import ru.medals.s3.di.s3Module
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class CheckTest : KoinTest {

	private val s3Repository by inject<S3Repository>(S3Repository::class.java)

	@Test
	fun `Проверка доступности s3`() {

		testApplication {
			startKoin {
				modules(s3Module)
			}

			val isOn = s3Repository.available()
			assertEquals(true, isOn)

			stopKoin()
		}
	}
}