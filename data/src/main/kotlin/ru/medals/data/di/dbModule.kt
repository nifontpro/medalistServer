package ru.medals.data.di

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.util.UpdateConfiguration
import ru.medals.domain.core.util.Constants
import java.util.concurrent.TimeUnit


val dbModule = module {

	single {

		val db = KMongo.createClient(
			MongoClientSettings
				.builder()
				.applyConnectionString(
					ConnectionString(
						System.getenv("MONGO_STRING") ?: ""
					)
				)
				.applyToClusterSettings { it.serverSelectionTimeout(3, TimeUnit.SECONDS) }
				.build()
		).coroutine
		UpdateConfiguration.updateOnlyNotNullProperties = true
		db
	}


	fun getDb(client: CoroutineClient): CoroutineDatabase = client.getDatabase(Constants.DATABASE_NAME)

	single {
		getDb(get())
	}

}