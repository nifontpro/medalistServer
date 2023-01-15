package ru.medals.data.gallery.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import ru.medals.domain.gallery.model.Folder

data class FolderCol(
	val name: String = "",
	val description: String? = null,
	val parentId: String = "",
	val childrenIds: List<String> = emptyList(),

	val createDate: Long = -1,
	val updateDate: Long? = null,

	val imageUrl: String = "",
	val imageKey: String = "",

	@BsonId
	val id: String = ""
) {
	fun toFolder() = Folder(
		name = name,
		description = description,
		parentId = parentId,
		childrenIds = childrenIds,
		createDate = createDate,
		updateDate = updateDate,
		imageUrl = imageUrl,
		imageKey = imageKey,
		id = id
	)
}

class FolderColBuild {
	var folderBuild = Folder()

	fun build() = FolderCol(
		name = folderBuild.name,
		description = folderBuild.description,
		parentId = folderBuild.parentId,
		createDate = System.currentTimeMillis(),
		id = ObjectId().toString()
	)
}

@EntityDslBuild
fun folderColBuild(block: FolderColBuild.() -> Unit) = FolderColBuild().apply(block).build()