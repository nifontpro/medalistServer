package ru.medals.domain.core.util

object Constants {

	// Режим проверки при разработке
	const val TEST_MODE = true

	const val DATABASE_NAME = "medals_server"

	/*    const val DEFAULT_PAGE_SIZE = 15
			const val DEFAULT_ACTIVITY_PAGE_SIZE = 15*/

	const val LIFE_TIME_REGISTER = 60 * 5 * 1000L // 5 min
	const val LIFE_TIME_REFRESH_TOKEN = 30 * 24 * 60 * 60 * 1000L // 30 day

	//	const val LIFE_TIME_ACCESS_TOKEN = 30 * 60 * 1000L // 30 min
	const val LIFE_TIME_ACCESS_TOKEN = 7 * 24 * 60 * 60 * 1000L // 7 day (dev)

	const val LOCAL_FOLDER: String = "/app/files"

	const val S3_BUCKET_NAME = "medalist"
	const val S3_BUCKET_SYSTEM = "medalistgallery"

	//password
	const val PASSWORD_LENGTH = 8 // Длина генерируемого пароля для сотрудников
	const val TEST_PASSWORD = "testPsw"
}