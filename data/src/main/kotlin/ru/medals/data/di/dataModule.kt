package ru.medals.data.di

import org.koin.dsl.module
import ru.medals.data.auth.repository.AuthRepositoryImpl
import ru.medals.data.award.repository.AwardRepositoryImpl
import ru.medals.data.company.repository.CompanyRepositoryImpl
import ru.medals.data.department.repository.DepartmentRepositoryImpl
import ru.medals.data.medal.repository.MedalRepositoryImpl
import ru.medals.data.message.repository.MessageRepositoryImpl
import ru.medals.data.reward.repository.RewardRepositoryImpl
import ru.medals.data.user.repository.UserRepositoryImpl
import ru.medals.domain.auth.repository.AuthRepository
import ru.medals.domain.award.repository.AwardRepository
import ru.medals.domain.company.repository.CompanyRepository
import ru.medals.domain.department.repository.DepartmentRepository
import ru.medals.domain.medal.repository.MedalRepository
import ru.medals.domain.message.repository.MessageRepository
import ru.medals.domain.reward.repository.RewardRepository
import ru.medals.domain.user.repository.UserRepository

/**
 * Внедрение репозиториев с применением инверсии зависимостей
 * Dependency Inversion & Dependency Injection
 */
val dataModule = module {
	single<AuthRepository> {
		AuthRepositoryImpl(db = get())
	}

	single<CompanyRepository> {
		CompanyRepositoryImpl(db = get(), s3repository = get())
	}

	single<DepartmentRepository> {
		DepartmentRepositoryImpl(db = get(), s3repository = get())
	}

	single<MedalRepository> {
		MedalRepositoryImpl(db = get(), s3repository = get())
	}

	single<RewardRepository> {
		RewardRepositoryImpl(db = get(), client = get())
	}

	single<UserRepository> {
		UserRepositoryImpl(db = get(), s3repository = get())
	}

	single<MessageRepository> {
		MessageRepositoryImpl(db = get())
	}

	single<AwardRepository> {
		AwardRepositoryImpl(db = get())
	}
}