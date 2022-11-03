package ru.medals.domain.reward.bussines.processor

import ru.medals.domain.core.bussines.IBaseProcessor
import ru.medals.domain.core.bussines.workers.*
import ru.medals.domain.core.bussines.workers.validation.validateCompanyIdEmpty
import ru.medals.domain.core.bussines.workers.validation.validateDirectorLevel
import ru.medals.domain.core.bussines.workers.validation.validation
import ru.medals.domain.reward.bussines.context.RewardContext
import ru.medals.domain.reward.bussines.validation.nominee.*
import ru.medals.domain.reward.bussines.validation.validateRewardActive
import ru.medals.domain.reward.bussines.validation.validateRewardId
import ru.medals.domain.reward.bussines.workers.*
import ru.medals.domain.reward.bussines.workers.info.getUserRewardsDb
import ru.medals.domain.reward.bussines.workers.nominee.nomineeUser
import ru.medals.domain.reward.bussines.workers.nominee.trimFieldNomineeRequest
import ru.otus.cor.rootChain

@Suppress("RemoveExplicitTypeArguments")
class RewardProcessor : IBaseProcessor<RewardContext> {

	override suspend fun exec(ctx: RewardContext) = businessChain.exec(ctx)

	companion object {

		private val businessChain = rootChain<RewardContext> {
			initStatus("Инициализация статуса")

			operation("Номинация сотрудника на премию", RewardContext.Command.NOMINEE_USER) {
				validation {
					validateNomineeUserIdNotEmpty("Проверка на непустой userId")
					validateNomineeCompanyIdNotEmpty("Проверка на непустой companyId")
					validateNomineeMedalIdNotEmpty("Проверка на непустой medalId")
					validateNomineeScore("Ценность награждения должна быть от 0 до 100%")
					validateNameHasContent("Проверка символов")
					trimFieldNomineeRequest("Очистка полей запроса и сохранение валидных")
				}
				getMedalByIdFromDb("Получаем медаль из БД")

				getUserByIdFromDb("Получаем сотрудника из БД и подготовка к авторизации")
				validateDirectorLevel("Уровень доступа - не ниже директор отдела")

				nomineeUser("Номинация сотрудника на премию")
			}

			operation("Поставить подпись под наградою", RewardContext.Command.PUT_SIGNATURE) {
				validateRewardId("Проверка rewardId")
				trimFieldRewardId("Очистка rewardId")
				getRewardByIdFromDb("Получаем информацию о награждении из БД")
				// !!!!!!! Проверить состояние награды
				doesUserMnc("Проверяем, является ли подписант ЧНК")
				putSignature("Поставить подпись под награждением")
			}

			operation("Наградить сотрудника", RewardContext.Command.REWARD_USER) {
				validateRewardId("Проверка rewardId")
				trimFieldRewardId("Очистка rewardId")
				getRewardInfoFromDb("Получаем награду из БД")
				validateRewardActive("Проверка, не присвоена ли уже награда")

				prepareAuth("Подготовка к авторизации")
				validateDirectorLevel("Уровень доступа - не ниже директор отдела")

				checkAllSignatures("Проверка подписей ЧНК")
				rewardUser("Наградить")
			}

			operation("Получить награды сотрудника", RewardContext.Command.GET_USER_REWARDS) {
				trimFieldUserIdAndCopyToValid("Очищаем userId")
				getUserRewardsDb("Получаем награды из БД")
			}

			operation("Полная информация о награждении", RewardContext.Command.GET_REWARD_INFO) {
				validateRewardId("Проверка rewardId")
				trimFieldRewardId("Очистка rewardId")
				getRewardInfoFromDb("Получаем полную информацию о награждении из БД")
			}

			operation("Получить награждение по id", RewardContext.Command.GET_REWARD_BY_ID) {
				validateRewardId("Проверка rewardId")
				trimFieldRewardId("Очистка rewardId")
				getRewardByIdFromDb("Получаем информацию о награждении из БД")
			}

			operation("Количество наград в компании", RewardContext.Command.GET_REWARD_COUNT) {
				validateCompanyIdEmpty("Проверка companyId")
				trimFieldCompanyIdAndCopyToValid("Очистка companyId")
				getRewardCountByCompanyDb("Получаем количество наград в компании")
			}

			finishOperation()
		}.build()
	}
}