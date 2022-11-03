package ru.medals.data.department.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.department.model.Department
import ru.medals.domain.image.model.ImageRef

data class DepartmentCol(
	val name: String,
	val description: String? = null,
	val companyId: String? = null, // id компании
	val imageUrl: String? = null,
	val imageKey: String? = null,
	val images: List<ImageRef>? = null,

	@BsonId
	val id: String = ObjectId().toString()
) {
	fun toDepartment() = Department(
		name = name,
		description = description,
		imageUrl = imageUrl,
		companyId = companyId ?: "",
		id = id,
	)
}
/*

fun Department.toDepartmentCol(isNew: Boolean = false) = DepartmentCol(
	name = name,
	description = description,
	imageUrl = imageUrl,
	companyId = companyId,
	id = if (isNew) ObjectId().toString() else id
)
*/
