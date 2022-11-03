package ru.medals.ktor.di

import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.dsl.module
import ru.medals.domain.auth.repository.AuthService
import ru.medals.ktor.auth.service.AuthServiceImpl
import ru.medals.ktor.auth.service.AuthVerify

val ktorModule = module {

	single<AuthService> { AuthServiceImpl(get(), get()) }

	single { HoconApplicationConfig(ConfigFactory.load()) }

	single { AuthVerify(get()) }

	single { Gson() }

}