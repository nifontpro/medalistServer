package ru.medals.ktor.reward

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.medals.domain.reward.bussines.processor.RewardProcessor
import ru.medals.domain.user.model.User.Companion.DIRECTOR
import ru.medals.domain.user.model.User.Companion.USER

fun Route.rewardRoutes() {

	route("reward") {
		val rewardProcessor = RewardProcessor()

		authenticate(DIRECTOR) {
			/**
			 * Номинировать сотрудника на премию
			 */
			post("nominee") {
				call.nomineeUser(rewardProcessor)
			}

			/**
			 * Утвердить номинированное награждение
			 */
			post("active") {
				call.rewardUser(rewardProcessor)
			}
		}

		authenticate(USER) {
			put("signature") {
				call.putSignature(rewardProcessor)
			}
		}

		/**
		 * Получить список награждений сотрудника
		 */
		post("user") {
			call.getUserRewards(rewardProcessor)
		}

		/**
		 * Получить полную информацию о награждении
		 */
		post("info") {
			call.getRewardInfo(rewardProcessor)
		}

		/**
		 * Получить награждение по id
		 */
		post {
			call.getRewardById(rewardProcessor)
		}

		/**
		 * Получить количество награждений в компании
		 */
		post("/count_c") {
			call.getRewardCountByCompany(rewardProcessor)
		}
	}
}