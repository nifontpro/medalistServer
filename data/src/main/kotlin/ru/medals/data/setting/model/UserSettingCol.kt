package ru.medals.data.setting.model

import org.bson.codecs.pojo.annotations.BsonId
import ru.medals.data.gallery.model.EntityDslBuild
import ru.medals.domain.setting.model.UserSetting

data class UserSettingCol(
	val showOnboarding: Boolean = false,
	val pageOnboarding: Int = -1,

	@BsonId
	val id: String = ""
) {
	fun toUserSetting() = UserSetting(
		userId = id,
		showOnboarding = showOnboarding,
		pageOnboarding = pageOnboarding,
	)
}

class UserSettingColBuild {
	var userId = ""
	var showOnboarding = false
	var pageOnboarding = 0

	fun build() = UserSettingCol(
		showOnboarding = showOnboarding,
		pageOnboarding = pageOnboarding,
		id = userId
	)
}

@EntityDslBuild
fun userSettingColBuild(block: UserSettingColBuild.() -> Unit) = UserSettingColBuild().apply(block).build()