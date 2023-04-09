package ru.medals.data.setting.repository

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import ru.medals.data.setting.model.UserSettingCol
import ru.medals.data.setting.model.userSettingColBuild
import ru.medals.data.setting.repository.SettingRepoErrors.Companion.errorGetUserSetting
import ru.medals.data.setting.repository.SettingRepoErrors.Companion.errorSettingSave
import ru.medals.domain.core.bussines.model.RepositoryData
import ru.medals.domain.setting.model.UserSetting
import ru.medals.domain.setting.repository.SettingRepository

class SettingRepositoryImpl(
	db: CoroutineDatabase,
) : SettingRepository {
	private val userSettings = db.getCollection<UserSettingCol>()

	override suspend fun saveUserSetting(userSetting: UserSetting): RepositoryData<UserSetting> {
		return try {
			val exist = userSettings.countDocuments(UserSettingCol::id eq userSetting.userId) > 0
			if (exist) {
				userSettings.updateOneById(
					id = userSetting.userId,
					update = set(
						UserSettingCol::showOnboarding setTo userSetting.showOnboarding,
						UserSettingCol::pageOnboarding setTo userSetting.pageOnboarding,
					)
				)
			} else {
				val newSettingCol = userSettingColBuild {
					userId = userSetting.userId
					showOnboarding = userSetting.showOnboarding
					pageOnboarding = userSetting.pageOnboarding
				}
				userSettings.insertOne(newSettingCol)
			}
			RepositoryData.success(data = userSetting)
		} catch (e: Exception) {
			errorSettingSave()
		}
	}

	override suspend fun getUserSetting(userId: String): RepositoryData<UserSetting> {
		return try {
			val setting = userSettings.findOneById(id = userId)
			RepositoryData(
				data = setting?.toUserSetting()?.copy(found = true) ?: UserSetting(userId = userId)
			)
		} catch (e: Exception) {
			errorGetUserSetting()
		}
	}

}
