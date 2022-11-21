package ru.medals.ktor.di

import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.dsl.module
import ru.medals.domain.auth.repository.AuthRepository
import ru.medals.ktor.auth.repository.AuthRepositoryImpl
import ru.medals.ktor.auth.repository.AuthVerify

val ktorModule = module {

	single<AuthRepository> { AuthRepositoryImpl(get()) }

	single { HoconApplicationConfig(ConfigFactory.load()) }

	single { AuthVerify(get()) }

	single { Gson() }

}