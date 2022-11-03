package ru.medals.data.medal.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.medal.model.Medal

data class MedalCol(
	val name: String? = null,
	val description: String? = null,
	val companyId: String? = null,
	val isSystem: Boolean = false,
	val score: Int? = null,
	val imageUrl: String? = null,
	val imageKey: String? = null,
	val images: List<ImageRef>? = null,

	@BsonId
	val id: String = ObjectId().toString()

) {
	fun toMedal() = Medal(
		name = name ?: "",
		description = description,
		companyId = companyId,
		isSystem = isSystem,
		score = score,
		imageUrl = imageUrl,
		id = id
	)
}

@Suppress("unused")
fun Medal.toMedalCol() = MedalCol(
	name = name,
	description = description,
	companyId = companyId,
	isSystem = isSystem,
	score = score,
	imageUrl = imageUrl,
	id = id
)
