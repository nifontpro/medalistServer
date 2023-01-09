package ru.medals.domain.gallery.bussines.processor

import ru.medals.domain.award.bussines.validate.*
import ru.medals.domain.award.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class GalleryProcessor : IBaseProcessor<GalleryContext> {

	override suspend fun exec(ctx: GalleryContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<GalleryContext> {
			initStatus("Инициализация статуса")

			operation("Добавить элемент", GalleryCommand.ADD) {

			}

			finishOperation()
		}.build()
	}
}