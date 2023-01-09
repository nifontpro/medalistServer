package ru.medals.ktor.gallery

import io.ktor.server.application.*
import ru.medals.domain.gallery.bussines.context.GalleryCommand
import ru.medals.domain.gallery.bussines.context.GalleryContext
import ru.medals.domain.gallery.bussines.processor.GalleryProcessor
import ru.medals.ktor.core.processGallery

suspend fun ApplicationCall.createGalleryItem(processor: GalleryProcessor) {
	val context = GalleryContext().apply { command = GalleryCommand.ADD }
	processGallery(context = context, processor = processor)
}