package ru.medals.domain.reward.bussines.utils

/**
 * Проверка, все ли члены номинационной комиссии поставили подписи под наградой
 * @param mncIds - ids членов комисии
 * @param rewardIds - ids подписавшихся членов комисии
 */
fun <T> checkRewardSignature(mncIds: List<T>, rewardIds: List<T>): Boolean {
	var isOk = true

	mncIds.forEach {
		if (!rewardIds.contains(it)) {
			isOk = false
			return@forEach
		}
	}

	return isOk
}