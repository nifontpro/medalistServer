package ru.medals.ktor.gallery.model.request

import ru.medals.ktor.core.request.BaseRequest

data class GetGalleryByFolderRequest(
	val folderId: String = "",

	override val page: Int? = null,
	override val pageSize: Int? = null,
	override val filter: String? = null,
	override val field: String? = null,
	override val direction: Int? = null

) : BaseRequest

