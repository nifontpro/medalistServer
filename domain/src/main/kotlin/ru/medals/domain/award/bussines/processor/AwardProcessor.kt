package ru.medals.domain.award.bussines.processor

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

class AwardProcessor : IBaseProcessor<AwardContext> {

	override suspend fun exec(ctx: AwardContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain {
			initStatus("Инициализация статуса")

			operation("Создать", AwardContext.Command.CREATE) {
				validateAwardNameEmpty("Проверка на непустое наименование награды")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				trimFieldAward("Очищаем поля награды")
				createAward("Создаем награждение")
			}

			operation("Обновить данные награды", AwardContext.Command.UPDATE) {
				validateAwardNameEmpty("Проверка на непустое наименование награды")
				validateAwardIdEmpty("Проверяем на непустой id")
				getCompanyIdByAwardId("Получаем companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				trimFieldAward("Очищаем поля награды")
				updateAward("Обновляем данные")
			}

			operation("Удалить награждение", AwardContext.Command.DELETE) {
				validateAwardIdEmpty("Проверяем на непустой id")
				getCompanyIdByAwardId("Получаем companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				validateAdminLevel("Уровень доступа - администратор")
				deleteAward("Удаляем награду")
			}

			operation("Получить по фильтру", AwardContext.Command.GET_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка на непустой companyId")
				trimFieldCompanyIdAndCopyToValid("Очищаем companyId")
				getAwardsByCompanyFromDb("Получаем все награждения")
			}

			operation("Получить по id", AwardContext.Command.GET_BY_ID) {
				validateAwardIdEmpty("Проверяем на непустой id")
				getAwardMedalByIdFromDb("Получаем награду и companyId")
			}

			finishOperation()
		}.build()
	}
}