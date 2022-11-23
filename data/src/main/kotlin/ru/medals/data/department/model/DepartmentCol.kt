package ru.medals.data.department.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.department.model.Department
import ru.medals.domain.image.model.IImages
import ru.medals.domain.image.model.ImageRef

data class DepartmentCol(
	val name: String,
	val description: String? = null,
	val companyId: String? = null, // id компании

	override val imageUrl: String? = null,
	override val imageKey: String? = null,
	override val images: List<ImageRef> = emptyList(),

	@BsonId
	val id: String = ObjectId().toString()
) : IImages {

	fun toDepartment() = Department(
		id = id,
		name = name,
		description = description,
		companyId = companyId ?: "",

		imageUrl = imageUrl,
		imageKey = imageKey,
		images = images,
	)
}

fun Department.toDepartmentCol(isNew: Boolean = true) = DepartmentCol(
	name = name,
	description = description,
	imageUrl = imageUrl,
	companyId = companyId,
	id = if (isNew) ObjectId().toString() else id
)
