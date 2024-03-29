package ru.medals.domain.user.bussines.processor

import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.validate.*
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.user.bussines.context.UserCommand
import ru.medals.domain.user.bussines.context.UserContext
import ru.medals.domain.user.bussines.validate.*
import ru.medals.domain.user.bussines.workers.*
import ru.otus.cor.rootChain
import ru.otus.cor.worker

@Suppress("RemoveExplicitTypeArguments")
class UserProcessor : IBaseProcessor<UserContext> {

	override suspend fun exec(ctx: UserContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<UserContext> {
			initStatus("Инициализация статуса")

			// Добавить проверку длины пароля и валидность email
			operation("Создать профиль сотрудника", UserCommand.CREATE) {
				validateUserLoginEmpty("Проверка на непустой login")
				worker("Подготовка полей к авторизации") {
					companyId = user.companyId
					departmentId = user.departmentId
				}
				validateCompanyIdEmpty("Проверка на непустой companyId")
				validateDepartmentIdUser("Проверка на непустой departmentId")
				trimFieldUser("Очищаем поля")
				validateSimpleAuth("Простая проверка прав доступа")
				extendedAuth("Расширенная проверка авторизации")
				validateUserWithLoginExist("Проверяем, нет ли сотрудника с таким же логином")
				// тоже с email in prod
				generatePassword("Генерируем пароль для сотрудника")
				sendLinkToUserEmailWithPassword("Отпраляем письмо с паролем")
				createUser("Создаем сотрудника")
			}

			operation("Удалить профиль сотрудника", UserCommand.DELETE) {
				validateUserIdEmpty("Проверяем на непустой userId")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				getUserByIdFromDb("Получаем сотрудника из БД")
				prepareAuthFromUser("Подготовка к авторизации")
				validateDirectorLevel("Уровень доступа - не ниже директор отдела")
				validateOwnerContainCompany("Проверяем наличие компаний у владельца")
				deleteUserAwards("Удаляем все награды у сотрудника")
				deleteUser("Удаление")
			}

			operation("Обновить профиль сотрудника", UserCommand.UPDATE) {
				validateUserIdEmptyLocal("Проверяем на непустой userId")
				validateUserLoginBlank("Проверка login, может быть null, если не меняем")
				trimFieldUser("Очищаем поля")
				worker("Подготовка к авторизации") { userIdValid = user.id }
				validateUserLevel("Уровень доступа - сотрудник")
				updateUser("Обновляем профиль")
			}

			operation("Обновить пароль сотрудника", UserCommand.UPDATE_PASSWORD) {
				validateUserIdEmpty("Проверяем на непустой userId")
				validatePasswordEmpty("Проверяем пароль")
				validateNewPasswordEmpty("Проверяем новый пароль")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				trimFieldPasswords("Очищаем поля паролей")
				getUserByIdHashPswFromDb("Получаем сотрудника из БД c хэш паролем")
				validatePassword("Проверяем пароль")
				updateUserPassword("Обновляем пароль")
			}

			operation("Получить сотрудника по id", UserCommand.GET_BY_ID) {
				validateUserIdEmpty("Проверяем на непустой userId")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				getUserByIdFromDb("Получаем сотрудника из БД")
//				infoUserMsg("Test")
			}

			operation("Получить сотрудника по id", UserCommand.GET_BY_ID_AWARDS) {
				validateUserIdEmpty("Проверяем на непустой userId")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				getUserByIdWithAwardsFromDb("Получаем сотрудника с наградами из БД")
			}

			operation("Получить сотрудника по id с названием отдела", UserCommand.GET_BY_ID_DEP_NAME) {
				validateUserIdEmpty("Проверяем на непустой userId")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				getUserByIdDepartNameFromDb("Получаем сотрудника из БД с названием отдела")
			}

			operation("Получить сотрудников отдела", UserCommand.GET_BY_DEPARTMENT) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				prepareFilter("Подготовка фильтра")
				getUsersByDepartmentFromDb("Получаем сотрудников отдела из БД")
			}

			operation("Получить сотрудников компании", UserCommand.GET_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				prepareFilter("Подготовка фильтра")
				getUsersByCompanyFromDb("Получаем сотрудников компании из БД")
			}

			operation("Получить сотрудников компании с названиями отделов", UserCommand.GET_BY_COMPANY_DEP_NAME) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				prepareFilter("Подготовка фильтра")
				getUsersByCompanyDepNameFromDb("Получаем сотрудников компании из БД")
			}

			operation("Получить сотрудников компании с наградами", UserCommand.GET_WITH_AWARDS) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				prepareFilter("Подготовка фильтра")
				getUsersByCompanyWithAwardsDb("Получаем сотрудников компании с наградами из БД")
			}

			operation("Получить сотрудников компании с наградами", UserCommand.GET_WITH_AWARDS_FULL) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				prepareFilter("Подготовка фильтра")
				getUsersByCompanyWithAwardsFullDb("Получаем сотрудников компании с наградами из БД")
			}

			operation("Получить лучших сотрудников компании", UserCommand.GET_BEST) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				validateLimit("Проверка максимального количества записей")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				getBestUsersByCompany("Получаем лучших сотрудников из БД")
			}

			operation("Получить начальников", UserCommand.GET_BOSSES) {
				prepareFilter("Подготовка фильтра")
				addYouOwnerOrAdmin("Добавляем Вас, если Вы Владелец или Администратор")
				addAdminsIfYouOwner("Если Вы Владелец, то добавляем администраторов выбранной компании")
			}

			operation("Число сотрудников в компании", UserCommand.COUNT_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				getUserCountByCompanyDb("Получаем количество сотрудников в компании из БД")
			}

			operation("Число сотрудников в отделе", UserCommand.COUNT_BY_DEPARTMENT) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getUserCountByDepartmentDb("Получаем количество сотрудников в отделе из БД")
			}

			operation("Число наград сотрудников в отделе", UserCommand.AWARD_COUNT_BY_DEPARTMENT) {
				validateDepartmentIdEmpty("Проверка на непустой departmentId")
				trimFieldDepartmentIdAndCopyToValid("Очистка departmentId")
				getUserAwardCountByDepartmentDb("Получаем количество наград сотрудников в отделе из БД")
			}

			operation("Число наград сотрудников в компании", UserCommand.AWARD_COUNT_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				getUserAwardCountByCompanyDb("Получаем количество наград сотрудников в компании из БД")
			}

			operation("Число наград сотрудников в компании по отделам", UserCommand.AWARD_COUNT_BY_COMPANY_AGR_DEP) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				getUserAwardCountByCompanyAgrDepDb("Получаем количество наград сотрудников в компании по отделам")
			}

			operation("Обновить основное изображение сотрудника", UserCommand.UPDATE_MAIN_IMAGE) {
				worker("Подготовка") { userIdValid = imageEntityId }
				validateUserLevel("Уровень доступа - сотрудник")
				updateUserMainImageS3("Обновляем изображение в S3")
			}

			operation("Удалить основное изображение сотрудника", UserCommand.DELETE_MAIN_IMAGE) {
				validateUserIdEmpty("Проверяем на непустой userId")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				validateUserLevel("Уровень доступа - сотрудник")
				deleteUserMainImageDb("Удаляем изображение в S3")
			}

			operation("Добавить изображение сотрудника", UserCommand.IMAGE_ADD) {
				validateUserIdEmpty("Проверяем на непустой userId")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				validateUserLevel("Уровень доступа - сотрудник")
				addUserImageToDb("Добавляем изображение в БД")
			}

			operation("Обновить изображение сотрудника", UserCommand.IMAGE_UPDATE) {
				validateUserIdEmpty("Проверяем на непустой userId")
				validateImageKeyEmpty("Проверяем imageKey")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				validateUserLevel("Уровень доступа - сотрудник")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				trimFieldFileData("Очищаем данные изображения")
				updateUserImageDb("Обновляем изображение")
			}

			operation("Удалить изображение сотрудника", UserCommand.IMAGE_DELETE) {
				validateUserIdEmpty("Проверяем на непустой userId")
				validateImageKeyEmpty("Проверяем imageKey")
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				validateUserLevel("Уровень доступа - сотрудник")
				trimFieldImageKeyAndCopyToValid("Очищаем imageKey")
				trimFieldFileData("Очищаем данные изображения")
				deleteUserImageDb("Удаляем изображение")
			}

			finishOperation()
		}.build()
	}
}