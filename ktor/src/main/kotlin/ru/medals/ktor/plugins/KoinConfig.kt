package ru.medals.ktor.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.medals.data.di.dataModule
import ru.medals.data.di.dbModule
import ru.medals.domain.di.domainModule
import ru.medals.ktor.di.ktorModule
import ru.medals.s3.di.s3Module

fun Application.configureKoin() {

	install(Koin) {
		slf4jLogger()
		modules(
			domainModule,
			ktorModule,
			dbModule,
			dataModule,
			s3Module,
		)
	}
}