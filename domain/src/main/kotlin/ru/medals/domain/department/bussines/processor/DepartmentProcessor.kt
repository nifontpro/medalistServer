package ru.medals.domain.department.bussines.processor

import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.validate.*
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.department.bussines.context.DepartmentContext
import ru.medals.domain.department.bussines.validate.validateDepartmentIdLocalEmpty
import ru.medals.domain.department.bussines.validate.validateDepartmentNameEmpty
import ru.medals.domain.department.bussines.workers.*
import ru.otus.cor.rootChain
import ru.otus.cor.worker

@Suppress("RemoveExplicitTypeArguments")
class DepartmentProcessor : IBaseProcessor<DepartmentContext> {

	override suspend fun exec(ctx: DepartmentContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<DepartmentContext> {
			initStatus("Инициализация статуса")

			operation("Создать отдел с пустыми полями", DepartmentContext.Command.CREATE_EMPTY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				createEmptyDepartment("Создаем отдел с пустыми полями")
			}

			operation("Создать отдел", DepartmentContext.Command.CREATE) {
				validateDepartmentNameEmpty("Проверка на непустое наименование отдела")
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				trimFieldDepartment("Очистка полей")
				doesDepartmentWithNameExist("Есть ли другой отдел с таким наименованием?")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				createDepartment("Создаем отдел")
			}

			operation("Удалить отдел", DepartmentContext.Command.DELETE) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getDepartmentByIdFromDb("Получаем отдел из БД и companyId для авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				doesDepartmentContainUsers("Проверяем, не содержит ли отдел сотрудников")
				deleteDepartment("Удаляем отдел")
			}

			operation("Получить отдел по id", DepartmentContext.Command.GET_BY_ID) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getDepartmentByIdFromDb("Получаем отдел из БД")
			}

			operation("Получить отделы компании", DepartmentContext.Command.GET_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				prepareFilter("Подготовка фильтра")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				getDepartmentsByCompanyFromDb("Получаем отделы компании")
			}

			operation("Получить количество отделов компании", DepartmentContext.Command.COUNT_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Подготовка к авторизации")
				getDepartmentsCountDb("Получаем количество отделов")
			}

			operation("Обновить данные отдела", DepartmentContext.Command.UPDATE) {
				validateDepartmentNameEmpty("Проверка на непустое наименование отдела")
				validateDepartmentIdLocalEmpty("Проверка на непустой id отдела")
				trimFieldDepartment("Очистка полей")
				getDepartmentCompanyIdValid("Получаем companyId для авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				doesOtherDepartmentWithNameExist("Есть ли другой отдел с таким наименованием?")
				updateDepartmentDb("Обновляем данные отдела")
			}

			operation("Обновить изображение отдела", DepartmentContext.Command.UPDATE_IMAGE_OLD) {
				worker("Подготовка") { departmentIdValid = imageEntityId }
				getDepartmentByIdFromDb("Получаем отдел из БД и готовим companyIdValid для авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				updateDepartmentImageS3("Обновляем изображение в S3")
			}

			operation("Добавить изображение отдела", DepartmentContext.Command.IMAGE_ADD) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getDepartmentCompanyIdValid("Получаем companyId для авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				trimFieldFileData("Очищаем данные изображения")
				addDepartmentImageToDb("Добавляем изображение в БД")
			}

			operation("Обновить изображение отдела", DepartmentContext.Command.IMAGE_UPDATE) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				validateImageKeyEmpty("Проверяем imageKey")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getDepartmentCompanyIdValid("Получаем companyId для авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				trimFieldFileData("Очищаем данные изображения")
				updateDepartmentImageDb("Обновляем изображение")
			}

			operation("Удалить изображение отдела", DepartmentContext.Command.IMAGE_DELETE) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				validateImageKeyEmpty("Проверяем imageKey")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getDepartmentCompanyIdValid("Получаем companyId для авторизации")
				validateAdminLevel("Уровень доступа - не ниже администратор компании")
				trimFieldImageKeyAndCopyToValid("imageKey")
				deleteDepartmentImageDb("Удаляем изображение")
			}

			operation("Получить ids", DepartmentContext.Command.GET_IDS) {
				getDepartmentIdsDb("Получаем ids")
			}

			finishOperation()
		}.build()
	}
}