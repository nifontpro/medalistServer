package ru.medals.domain.company.bussines.processor

import ru.medals.domain.company.bussines.context.CompanyCommand
import ru.medals.domain.company.bussines.context.CompanyContext
import ru.medals.domain.company.bussines.validate.validateCompanyNameEmpty
import ru.medals.domain.company.bussines.validate.validateOwnerPrincipalUser
import ru.medals.domain.company.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.validate.*
import ru.medals.domain.core.bussines.workers.*
import ru.otus.cor.rootChain
import ru.otus.cor.worker

@Suppress("RemoveExplicitTypeArguments")
class CompanyProcessor : IBaseProcessor<CompanyContext> {

	override suspend fun exec(ctx: CompanyContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<CompanyContext> {
			initStatus("Инициализация статуса")

			operation("Создать компанию с пустыми полями", CompanyCommand.CREATE_EMPTY) {
				validateOwnerPrincipalUser("Проверка на владельца")
				createEmptyCompany("Создаем компанию")
			}

			operation("Создать компанию", CompanyCommand.CREATE) {
				validateOwnerPrincipalUser("Проверка на владельца")
				validateCompanyNameEmpty("Проверка наименования компании")
				doesCompanyWithNameExist("Проверка, есть ли компания с таким наименованием")
				createCompany("Создаем компанию")
			}

			operation("Обновить данные компании", CompanyCommand.UPDATE) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				validateCompanyNameEmpty("Проверка наименования компании")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - администратор")
				trimFieldCompany("Очищаем поля")
				doesOtherCompanyWithNameExist("Проверка, есть ли у Владельца компания с таким наименованием")
				updateCompany("Обновляем данные")
			}

			operation("Удалить компанию", CompanyCommand.DELETE) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateOwnerLevel("Уровень доступа - владелец компаний")
				doesCompanyContainsDepartments("Содержит ли компания отделы?")
				deleteCompany("Удаляем компанию")
			}

			operation("Получить все компании", CompanyCommand.GET_ALL) {
				getAllCompanies("Получаем компании из БД")
			}

			operation("Получить по id", CompanyCommand.GET_BY_ID) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем и копируем")
				getCompanyById("Получаем компанию из БД")
			}

			operation("Получить компании владельца", CompanyCommand.GET_BY_OWNER) {
				prepareFilter("Подготовка фильтра")
				getOwnerCompanies("Получаем компании из БД")
			}

			operation("Получить количество компаний владельца", CompanyCommand.GET_COUNT_BY_OWNER) {
				getOwnerCompanyCount("Получаем количество компаний из БД")
			}

			operation("Обновить основное изображение компании", CompanyCommand.UPDATE_MAIN_IMAGE) {
				worker("Подготовка") { companyIdValid = imageEntityId }
				validateAdminLevel("Уровень доступа - администратор")
				updateCompanyMainImageS3("Обновляем изображение в S3")
			}

			operation("Удалить основное изображение компании", CompanyCommand.DELETE_MAIN_IMAGE) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем и копируем")
				validateAdminLevel("Уровень доступа - администратор")
				deleteCompanyMainImageDb("Удаляем основное изображение")
			}

			operation("Добавить изображение компании", CompanyCommand.IMAGE_ADD) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateOwnerLevel("Уровень доступа - владелец компаний")
				trimFieldFileData("Очищаем данные изображения")
				addCompanyImageToDb("Добавляем изображение в БД")
			}

			operation("Обновить изображение компании", CompanyCommand.IMAGE_UPDATE) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				validateImageKeyEmpty("Проверяем imageKey")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				validateOwnerLevel("Уровень доступа - владелец компаний")
				trimFieldFileData("Очищаем данные изображения")
				updateCompanyImageDb("Обновляем изображение")
			}

			operation("Удалить изображение компании", CompanyCommand.IMAGE_DELETE) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				validateImageKeyEmpty("Проверяем imageKey")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				validateOwnerLevel("Уровень доступа - владелец компаний")
				deleteCompanyImageDb("Удаляем изображение")
			}

			finishOperation()
		}.build()
	}
}