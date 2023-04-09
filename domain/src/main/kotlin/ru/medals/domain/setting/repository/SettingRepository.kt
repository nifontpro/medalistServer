package ru.medals.domain.setting.repository

import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.setting.model.UserSetting

interface SettingRepository {
	suspend fun saveUserSetting(userSetting: UserSetting): RepositoryData<UserSetting>
	suspend fun getUserSetting(userId: String): RepositoryData<UserSetting>
}