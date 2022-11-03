package ru.medals.domain.di

import org.koin.dsl.module
import ru.medals.domain.auth.bussines.processor.AuthProcessor
import ru.medals.domain.company.bussines.processor.CompanyProcessor
import ru.medals.domain.department.bussines.processor.DepartmentProcessor
import ru.medals.domain.medal.bussines.processor.MedalProcessor
import ru.medals.domain.user.bussines.processor.UserProcessor

val domainModule = module {

	single { AuthProcessor() }

	single { CompanyProcessor() }

	single { DepartmentProcessor() }

	single { MedalProcessor() }

	single { UserProcessor() }

}