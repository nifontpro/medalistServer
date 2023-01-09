package ru.medals.data.user.model

import org.bson.codecs.pojo.annotations.BsonId
import ru.medals.domain.user.model.UserLite

data class UserLiteCol(
	val name: String? = null,
	val patronymic: String? = null,
	val lastname: String? = null,
	val post: String? = null,
	val imageUrl: String? = null,

	@BsonId
	val id: String = ""
) {

	fun toUserLite(): UserLite {
		return UserLite(
			id = id,
			name = name,
			patronymic = patronymic,
			lastname = lastname,
			post = post,
			imageUrl = imageUrl,
		)
	}
}