package ru.medals.data.company.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.company.model.Company
import ru.medals.domain.image.model.ImageRef

data class CompanyCol(
	val name: String,
	val description: String? = null,

	val ownerId: String? = null, // id владельца компании
	val imageUrl: String? = null,
	val imageKey: String? = null,
	val images: List<ImageRef>? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toCompany() = Company(
		name = name,
		description = description,
		ownerId = ownerId ?: "",
		imageUrl = imageUrl,
		id = id
	)
}
