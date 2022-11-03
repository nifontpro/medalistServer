package nominee

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.medals.domain.core.bussines.ContextState
import ru.medals.domain.core.bussines.IBaseCommand
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.bussines.processor.RewardProcessor
import ru.medals.domain.reward.model.Nominee
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class BizValidationNomineeTest {

	private val cmd: IBaseCommand = RewardContext.Command.NOMINEE_USER
	private val processor by lazy { RewardProcessor() }

	@Test
	fun `Валидный запрос`(): Unit = runTest {
		val ctx = RewardContext(
			nominee = Nominee(
				name = "Тестовое награждение",
				description = "Описание",
				score = 45,
				userId = "not-valid",
				medalId = "not-valid",
				companyId = "not-valid"
			)
		).apply {
			state = ContextState.NONE
			command = cmd
		}

		processor.exec(ctx)
		println("ERRORS: " + ctx.errors)
		assertEquals(1, ctx.errors.size)
		assertEquals(ContextState.FAILING, ctx.state)
		val error = ctx.errors.firstOrNull()
		assertEquals("db", error?.group)
		assertEquals("medal", error?.field)
	}

	@Test
	fun `Недопустимые символы в имени`(): Unit = runTest {
		val ctx = RewardContext(
			nominee = Nominee(
				name = " %^ ",
				description = "Описание",
				score = 45,
				userId = "not-valid",
				medalId = "not-valid",
				companyId = "not-valid"
			)
		).apply {
			state = ContextState.NONE
			command = cmd
		}

		processor.exec(ctx)
		assertEquals(1, ctx.errors.size)
		assertEquals(ContextState.FAILING, ctx.state)
		val error = ctx.errors.firstOrNull()
		assertEquals("validation", error?.group)
		assertEquals("name", error?.field)
	}

	@Test
	fun `Недопустимый рейтинг награждения`(): Unit = runTest {
		val ctx = RewardContext(
			nominee = Nominee(
				name = " За труды ",
				description = "Описание",
				score = 145,
				userId = "not-valid",
				medalId = "not-valid",
				companyId = "not-valid"
			)
		).apply {
			state = ContextState.NONE
			command = cmd
		}

		processor.exec(ctx)
		println("ERRORS: " + ctx.errors)
		assertEquals(1, ctx.errors.size)
		assertEquals(ContextState.FAILING, ctx.state)
		val error = ctx.errors.firstOrNull()
		assertEquals("validation", error?.group)
		assertEquals("score", error?.field)
	}
}