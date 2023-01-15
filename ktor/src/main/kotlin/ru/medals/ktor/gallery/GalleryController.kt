package ru.medals.ktor.gallery

import io.ktor.server.application.*
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor
import ru.medals.domain.gallery.model.Folder
import ru.medals.domain.gallery.model.GalleryItem
import ru.medals.ktor.core.process
import ru.medals.ktor.core.processGallery
import ru.medals.ktor.gallery.mappers.fromTransport
import ru.medals.ktor.gallery.mappers.toTransportGetFolder
import ru.medals.ktor.gallery.mappers.toTransportGetGallery
import ru.medals.ktor.gallery.mappers.toTransportGetGalleryItem
import ru.medals.ktor.gallery.model.request.CreateFolderRequest
import ru.medals.ktor.gallery.model.request.GalleryItemDeleteRequest
import ru.medals.ktor.gallery.model.request.GetGalleryByFolderRequest

suspend fun ApplicationCall.createGalleryItem(processor: GalleryProcessor) {
	val context = GalleryContext().apply { command = GalleryCommand.ADD }
	processGallery(context = context, processor = processor)
}

suspend fun ApplicationCall.updateGalleryItem(processor: GalleryProcessor) {
	val context = GalleryContext().apply { command = GalleryCommand.UPDATE }
	processGallery(context = context, processor = processor)
}

suspend fun ApplicationCall.getGalleryByFolder(processor: GalleryProcessor) =
	process<GetGalleryByFolderRequest, List<GalleryItem>, GalleryContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetGallery() }
	)

suspend fun ApplicationCall.deleteGalleryItem(processor: GalleryProcessor) =
	process<GalleryItemDeleteRequest, GalleryItem, GalleryContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetGalleryItem() }
	)

suspend fun ApplicationCall.createFolder(processor: GalleryProcessor) =
	process<CreateFolderRequest, Folder, GalleryContext>(
		processor = processor,
		fromTransport = { request -> fromTransport(request) },
		toTransport = { toTransportGetFolder() }
	)