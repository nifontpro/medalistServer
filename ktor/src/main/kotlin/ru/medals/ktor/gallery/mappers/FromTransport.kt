package ru.medals.ktor.gallery.mappers

import ru.medals.domain.core.bussines.model.BaseQuery
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.model.Folder
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.ktor.gallery.model.request.CreateFolderRequest
import ru.medals.ktor.gallery.model.request.GalleryItemDeleteRequest
import ru.medals.ktor.gallery.model.request.GetFoldersRequest
import ru.medals.ktor.gallery.model.request.GetGalleryByFolderRequest

fun GalleryContext.fromTransport(request: GetGalleryByFolderRequest) {
	command = GalleryCommand.GET_BY_FOLDER

	galleryItem = GalleryItem(folderId = request.folderId)

	baseQuery = BaseQuery(
		page = request.page,
		pageSize = request.pageSize,
		filter = request.filter,
		field = request.field,
		direction = request.direction
	)
}

fun GalleryContext.fromTransport(request: GalleryItemDeleteRequest) {
	command = GalleryCommand.DELETE
	galleryItem = GalleryItem(id = request.itemId)
}

fun GalleryContext.fromTransport(request: CreateFolderRequest) {
	command = GalleryCommand.CREATE_FOLDER
	folder = Folder(
		name = request.name,
		parentId = request.parentId
	)
}

fun GalleryContext.fromTransport(request: GetFoldersRequest) {
	command = GalleryCommand.GET_FOLDERS
	folder = Folder(
		parentId = request.parentId
	)
}