package data.time

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.image.repository.ImageRepository
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class TimestampTest : KoinTest {

	private val imageRepository by inject<ImageRepository>(ImageRepository::class.java)

	@Test
	fun `Проверка времени`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule)
			}

			val timestamp = System.currentTimeMillis()
			val id = imageRepository.createTime(timestamp = timestamp)
			val time = id?.let {
				imageRepository.getTimeById(it)
			}

			assertEquals(timestamp, time)

			stopKoin()
		}
	}
}