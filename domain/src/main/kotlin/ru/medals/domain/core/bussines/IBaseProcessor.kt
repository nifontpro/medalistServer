package ru.medals.domain.core.bussines

interface IBaseProcessor<T> {
	suspend fun exec(ctx: T)
}
