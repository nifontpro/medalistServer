package ru.medals.ktor.gallery.mappers

import ru.medals.domain.core.bussines.model.BaseQuery
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.ktor.gallery.model.request.GetGalleryByFolderRequest

fun GalleryContext.fromTransport(request: GetGalleryByFolderRequest) {
	command = GalleryCommand.GET_BY_FOLDER

	galleryItem = galleryItem.copy(folderId = request.folderId)

	baseQuery = BaseQuery(
		page = request.page,
		pageSize = request.pageSize,
		filter = request.filter,
		field = request.field,
		direction = request.direction
	)
}
