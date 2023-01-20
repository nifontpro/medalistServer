package ru.medals.domain.gallery.bussines.processor

import ru.medals.domain.award.bussines.validate.*
import ru.medals.domain.award.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.validate.gallery.validateGalleryItemId
import ru.medals.domain.core.bussines.validate.query.validateBaseQuery
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.workers.db.getGalleryItemById
import ru.medals.domain.core.bussines.workers.query.prepareBaseQuery
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.validate.*
import ru.medals.domain.gallery.bussines.validate.folder.validateFolderName
import ru.medals.domain.gallery.bussines.validate.folder.validateParentId
import ru.medals.domain.gallery.bussines.workers.db.*
import ru.medals.domain.gallery.bussines.workers.folder.db.checkParentFolderExist
import ru.medals.domain.gallery.bussines.workers.folder.db.createFolder
import ru.medals.domain.gallery.bussines.workers.folder.db.getFolders
import ru.medals.domain.gallery.bussines.workers.folder.trimFieldFolderItem
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
				validateGalleryFolderId("Проверка folderId")
				trimFieldGalleryItem("Очищаем поля")
				addGalleryItem("Добавляем объект в галерею")
			}

			operation("Удалить объект галереи", GalleryCommand.DELETE) {
				validateGalleryItemId("Проверяем id")
				getGalleryItemById("Получаем объект")
				checkAwardLinkExist("Проверка, есть ли ссылки на объект у наград")
				deleteGalleryItem("Удаляем объект галереи")
			}

			operation("Обновить объект галереи", GalleryCommand.UPDATE) {
				validateGalleryItemId("Проверяем id")
				validateGalleryFolderId("Проверка folderId")
				validateGalleryItemName("Проверяем наименование")
				trimFieldGalleryItem("Очищаем поля")
				getGalleryItemById("Получаем объект")
				updateGalleryItemImage("Обновляем изображение")
				updateGalleryItem("Обновляем поля объекта")
			}

			operation("Получить список объектов галереи", GalleryCommand.GET_BY_FOLDER) {
				validateGalleryFolderId("Проверка folderId")
				validateBaseQuery("Проверка базового запроса")
				prepareBaseQuery("Подготовка базового запроса")
				getGalleryByFolder("Получаем список объектов в галереи")
			}

			operation("Создать папку", GalleryCommand.CREATE_FOLDER) {
				validateFolderName("Проверяем наименование")
				validateParentId("Проверяем parentId")
				checkParentFolderExist("Проверяем наличие родительской папки")
				trimFieldFolderItem("Очищаем поля")
				createFolder("Создаем папку")
			}

			operation("Получить список папок", GalleryCommand.GET_FOLDERS) {
				validateParentId("Проверяем parentId")
				getFolders("Получаем список папок")
			}

			finishOperation()
		}.build()
	}
}