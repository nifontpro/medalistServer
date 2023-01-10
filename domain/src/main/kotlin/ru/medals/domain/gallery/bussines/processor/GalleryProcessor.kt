package ru.medals.domain.gallery.bussines.processor

import ru.medals.domain.award.bussines.validate.*
import ru.medals.domain.award.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.workers.addGalleryItem
import ru.medals.domain.gallery.workers.getGalleryByFolder
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class GalleryProcessor : IBaseProcessor<GalleryContext> {

	override suspend fun exec(ctx: GalleryContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<GalleryContext> {
			initStatus("Инициализация статуса")

			operation("Добавить элемент в галлерею", GalleryCommand.ADD) {
				addGalleryItem("Добавляем объект в галлерею")
			}

			operation("Получить список объектов галереи", GalleryCommand.GET_BY_FOLDER) {
				getGalleryByFolder("Получаем список объектов в галереи")
			}

			finishOperation()
		}.build()
	}
}