package ru.medals.data.company.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.company.model.Company
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class CompanyCol(
	val name: String,
	val description: String? = null,
	val ownerId: String? = null, // id владельца компании

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
): IImages {
	fun toCompany() = Company(
		id = id,
		name = name,
		description = description,
		ownerId = ownerId ?: "",

		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,
	)
}
