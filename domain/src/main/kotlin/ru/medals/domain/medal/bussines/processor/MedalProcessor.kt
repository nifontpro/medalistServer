package ru.medals.domain.medal.bussines.processor

import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.workers.validation.*
import ru.medals.domain.medal.bussines.context.MedalContext
import ru.medals.domain.medal.bussines.validate.validateMedalIdEmpty
import ru.medals.domain.medal.bussines.validate.validateMedalNameEmpty
import ru.medals.domain.medal.bussines.validate.validateMedalScore
import ru.medals.domain.medal.bussines.validate.validateSystemMedal
import ru.medals.domain.medal.bussines.workers.*
import ru.otus.cor.rootChain
import ru.otus.cor.worker

@Suppress("RemoveExplicitTypeArguments")
class MedalProcessor : IBaseProcessor<MedalContext> {

	override suspend fun exec(ctx: MedalContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<MedalContext> {
			initStatus("Инициализация статуса")

			operation("Создать медаль", MedalContext.Command.CREATE) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				createMedal("Создаем новую медаль")
			}

			operation("Удалить медаль", MedalContext.Command.DELETE) {
				validateMedalIdEmpty("Проверяем medalId")
				getMedalByIdFromDb("Получаем медаль из БД")
				validateSystemMedal("Проверка, не является ли системной")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				deleteMedal("Удаляем медаль")
			}

			operation("Получить медали компании", MedalContext.Command.GET_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка")
				prepareFilter("Подготовка фильтра")
				getCompanyMedalsFromDb("Получаем медали компании")
			}

			operation("Получить по id", MedalContext.Command.GET_BY_ID) {
				validateMedalIdEmpty("Проверяем medalId")
				getMedalByIdFromDb("Получаем медаль из БД")
			}

			operation("Получить количество медалей в компании", MedalContext.Command.GET_COUNT) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка")
				getCountMedalsFromDb("Получаем количество медалей в компании")
			}

			operation("Обновить данные медали", MedalContext.Command.UPDATE) {
				validateMedalIdEmpty("Проверяем medalId")
				validateMedalNameEmpty("Проверяем наименование")
				validateMedalScore("Проверяем ценность")
				getMedalByIdFromDb("Получаем из БД, получаем companyId для авторизации")
				validateSystemMedal("Проверка, не является ли системной или пусто companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				trimFieldMedal("Очищаем поля и копируем companyId из медали БД")
				updateMedal("Обновляем данные")
			}

			operation("Обновить изображение медали", MedalContext.Command.UPDATE_IMAGE) {
				worker("Подготовка к получению из БД") { medalId = imageEntityId }
				getMedalByIdFromDb("Получаем из БД, получаем companyId для авторизации")
				validateSystemMedal("Проверка, не является ли системной или пусто companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				updateMedalImageS3("Обновляем изображение в S3")
			}

			operation("Добавить изображение медали", MedalContext.Command.IMAGE_ADD) {
				validateMedalIdEmpty("Проверяем medalId")
				getMedalByIdFromDb("Получаем из БД, получаем companyId для авторизации")
				validateSystemMedal("Проверка, не является ли системной или пусто companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				trimFieldFileData("Очищаем данные изображения")
				addMedalImageToDb("Добавляем изображение в БД")
			}

			operation("Обновить изображение медали", MedalContext.Command.IMAGE_UPDATE) {
				validateMedalIdEmpty("Проверяем medalId")
				validateImageKeyEmpty("Проверяем imageKey")
				getMedalByIdFromDb("Получаем из БД, получаем companyId для авторизации")
				validateSystemMedal("Проверка, не является ли системной или пусто companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				trimFieldFileData("Очищаем данные изображения")
				updateMedalImageDb("Обновляем изображение")
			}

			operation("Удалить изображение медали", MedalContext.Command.IMAGE_DELETE) {
				validateMedalIdEmpty("Проверяем medalId")
				validateImageKeyEmpty("Проверяем imageKey")
				getMedalByIdFromDb("Получаем из БД, получаем companyId для авторизации")
				validateSystemMedal("Проверка, не является ли системной или пусто companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				deleteMedalImageDb("Удаляем изображение")
			}

			finishOperation()
		}.build()
	}
}