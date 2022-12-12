import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class GeneratePasswordTest {

	@Test
	fun `Проверка генерации пароля`() {
		val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
		val password = List(8) { chars.random() }.joinToString("")
		println(password)
	}
}