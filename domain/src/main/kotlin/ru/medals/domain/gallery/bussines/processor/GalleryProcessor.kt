package ru.medals.domain.gallery.bussines.processor

import ru.medals.domain.award.bussines.validate.*
import ru.medals.domain.award.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.validate.query.validateBaseQuery
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.workers.query.prepareBaseQuery
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.validate.*
import ru.medals.domain.gallery.bussines.workers.db.addGalleryItem
import ru.medals.domain.gallery.bussines.workers.db.deleteGalleryItem
import ru.medals.domain.gallery.bussines.workers.db.getGalleryByFolder
import ru.medals.domain.gallery.bussines.workers.db.getGalleryItem
import ru.medals.domain.gallery.bussines.workers.trimFieldGalleryItem
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class GalleryProcessor : IBaseProcessor<GalleryContext> {

	override suspend fun exec(ctx: GalleryContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<GalleryContext> {
			initStatus("Инициализация статуса")

			operation("Добавить объект в галерею", GalleryCommand.ADD) {
				validateGalleryItemName("Проверяем наименование")
				validateGalleryFolderId("Провурка folderId")
				validateFolderId("Проверка folderId")
				trimFieldGalleryItem("Очищаем поля")
				addGalleryItem("Добавляем объект в галерею")
			}

			operation("Удалить объект галереи", GalleryCommand.DELETE) {
				validateGalleryItemId("Проверяем id")
				getGalleryItem("Получаем объект")
				validateLinkExist("Проверяем наличие ссылок")
				deleteGalleryItem("Удаляем объект галереи")
			}

			operation("Получить список объектов галереи", GalleryCommand.GET_BY_FOLDER) {
				validateGalleryFolderId("Провурка folderId")
				validateBaseQuery("Проверка базового запроса")
				prepareBaseQuery("Подготовка базового запроса")
				getGalleryByFolder("Получаем список объектов в галереи")
			}

			finishOperation()
		}.build()
	}
}