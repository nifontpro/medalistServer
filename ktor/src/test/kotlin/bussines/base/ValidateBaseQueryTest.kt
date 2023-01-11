package bussines.base

import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.model.BaseQuery
import ru.medals.domain.core.bussines.model.BaseQueryValid
import ru.medals.domain.core.util.separator
import ru.medals.domain.di.domainModule
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.s3.di.s3Module
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class ValidateBaseQueryTest : KoinTest {

	private val galleryProcessor by lazy { GalleryProcessor() }

	@Test
	fun `Проверка базового запроса`() {

		testApplication {
			startKoin {
				modules(dbModule, dataModule, domainModule, s3Module)
			}
			val ctx = GalleryContext().apply {
				command = GalleryCommand.GET_BY_FOLDER
				state = ContextState.NONE
				galleryItem = GalleryItem(folderId = "medal")

				baseQuery = BaseQuery(
					page = 0,
					pageSize = null,
					filter = "filter   ",
					field = "   name   "
				)
			}
			galleryProcessor.exec(ctx)
			var baseQueryValid = BaseQueryValid(
					page = 0, pageSize = Int.MAX_VALUE, filter = "filter", field = "name", direction = 1
				)
			assertEquals(baseQueryValid, ctx.baseQueryValid)

			/**
			 * Actual
			 */
			ctx.baseQuery = BaseQuery(
					page = 10,
					pageSize = 34,
					filter = "  xx  ",
					field = "name",
					direction = -10,
				)

			galleryProcessor.exec(ctx)
			baseQueryValid = BaseQueryValid(
				page = 10, pageSize = 34, filter = "xx", field = "name", direction = -1
			)
			separator()
			println(ctx.errors)
			println(ctx.baseQueryValid)
			assertEquals(baseQueryValid, ctx.baseQueryValid)

			stopKoin()
		}
	}
}