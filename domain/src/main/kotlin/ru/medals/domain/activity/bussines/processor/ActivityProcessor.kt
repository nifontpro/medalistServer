package ru.medals.domain.activity.bussines.processor

import ru.medals.domain.activity.bussines.context.ActivityCommand
import ru.medals.domain.activity.bussines.context.ActivityContext
import ru.medals.domain.activity.bussines.validate.validateAwardNameEmpty
import ru.medals.domain.activity.bussines.validate.validatePage
import ru.medals.domain.activity.bussines.validate.validatePageSize
import ru.medals.domain.activity.bussines.validate.validateSort
import ru.medals.domain.activity.bussines.workers.getActivitiesByCompany
import ru.medals.domain.activity.bussines.workers.prepareActivityRequest
import ru.medals.domain.award.bussines.validate.*
import ru.medals.domain.award.bussines.workers.*
import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.validate.prepareFilter
import ru.medals.domain.core.bussines.validate.validateCompanyIdEmpty
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class ActivityProcessor : IBaseProcessor<ActivityContext> {

	override suspend fun exec(ctx: ActivityContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<ActivityContext> {
			initStatus("Инициализация статуса")

			operation("Получить активность в компании", ActivityCommand.GET_BY_COMPANY) {
				validateCompanyIdEmpty("Проверка companyId")
				trimFieldCompanyIdAndCopyToValid("Копируем")
				validateAwardNameEmpty("Проверка дат")
				validateSort("Проверка поля сортировки")
				validatePage("Проверяем страницу")
				validatePageSize("Проверяем размер страниц")

				prepareFilter("Очищаем фильтр")
				prepareActivityRequest("Подготавливаем запрос")

				getActivitiesByCompany("Получаем активность в компании")
			}

			finishOperation()
		}.build()
	}
}