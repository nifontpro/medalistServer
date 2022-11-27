package ru.medals.domain.award.bussines.processor

import ru.medals.domain.award.bussines.context.AwardCommand
import ru.medals.domain.award.bussines.context.AwardContext
import ru.medals.domain.award.bussines.validate.validateAwardIdEmpty
import ru.medals.domain.award.bussines.validate.validateAwardNameEmpty
import ru.medals.domain.award.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.finishOperation
import ru.medals.domain.core.bussines.workers.initStatus
import ru.medals.domain.core.bussines.workers.operation
import ru.medals.domain.core.bussines.workers.trimFieldCompanyIdAndCopyToValid
import ru.medals.domain.core.bussines.workers.validation.validateAdminLevel
import ru.medals.domain.core.bussines.workers.validation.validateCompanyIdEmpty
import ru.otus.cor.rootChain
import ru.otus.cor.worker

@Suppress("RemoveExplicitTypeArguments")
class AwardProcessor : IBaseProcessor<AwardContext> {

	override suspend fun exec(ctx: AwardContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<AwardContext> {
			initStatus("Инициализация статуса")

			operation("Создать", AwardCommand.CREATE) {
				validateAwardNameEmpty("Проверка на непустое наименование награды")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				trimFieldAward("Очищаем поля награды")
				createAward("Создаем награждение")
			}

			operation("Обновить данные награды", AwardCommand.UPDATE) {
				validateAwardNameEmpty("Проверка на непустое наименование награды")
				validateAwardIdEmpty("Проверяем на непустой id")
				getCompanyIdByAwardId("Получаем companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				trimFieldAward("Очищаем поля награды")
				updateAward("Обновляем данные")
			}

			operation("Удалить награждение", AwardCommand.DELETE) {
				validateAwardIdEmpty("Проверяем на непустой id")
				getCompanyIdByAwardId("Получаем companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				deleteAward("Удаляем награду")
			}

			operation("Получить награды в компании", AwardCommand.GET_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				getAwardsByCompanyFromDb("Получаем награждения")
			}

			operation("Получить награды в компании с сотрудниками", AwardCommand.GET_BY_COMPANY_WITH_USERS) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				getAwardsByCompanyWithUsersFromDb("Получаем награждения с сотрудниками")
			}

			operation("Получить по id", AwardCommand.GET_BY_ID) {
				validateAwardIdEmpty("Проверяем на непустой id")
				getAwardByIdFromDb("Получаем награду")
			}

			operation("Получить по id с сотрудниками", AwardCommand.GET_BY_ID_WITH_USERS) {
				validateAwardIdEmpty("Проверяем на непустой id")
				getAwardByIdWithUsersFromDb("Получаем награду со связанными сотрудниками")
			}

			operation("Обновить изображение награды", AwardCommand.UPDATE_IMAGE_OLD) {
				worker("Подготовка к получению из БД") { awardId = imageEntityId }
				getCompanyIdByAwardId("Получаем companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				updateAwardImageS3("Обновляем изображение в S3")
			}

			finishOperation()
		}.build()
	}
}