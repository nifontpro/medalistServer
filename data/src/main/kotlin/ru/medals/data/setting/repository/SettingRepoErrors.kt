package ru.medals.data.setting.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.core.bussines.model.RepositoryError

interface SettingRepoErrors {
	companion object {

		private const val REPO = "setting"

		fun errorSettingSave() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "save",
				description = "Ошибка сохранения настроек"
			)
		)

		fun errorGetUserSetting() = RepositoryData.error(
			error = RepositoryError(
				repository = REPO,
				violationCode = "get user setting",
				description = "Ошибка получения настроек сотрудника"
			)
		)

	}
}