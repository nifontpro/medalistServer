package ru.medals.data.medal.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef
import ru.medals.domain.medal.model.Medal

data class MedalCol(
	val name: String? = null,
	val description: String? = null,
	val companyId: String? = null,
	val isSystem: Boolean = false,
	val score: Int? = null,

	override val sysImage: Boolean = false,
	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()

): IImages {
	fun toMedal() = Medal(
		id = id,
		name = name ?: "",
		description = description,
		companyId = companyId,
		isSystem = isSystem,
		score = score,

		sysImage = sysImage,
		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,
	)
}